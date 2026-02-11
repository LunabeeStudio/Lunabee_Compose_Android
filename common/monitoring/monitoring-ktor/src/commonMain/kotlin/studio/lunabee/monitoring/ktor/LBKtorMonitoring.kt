/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package studio.lunabee.monitoring.ktor

import studio.lunabee.monitoring.core.LBMonitoring
import studio.lunabee.monitoring.core.LBPayload
import studio.lunabee.monitoring.core.LBRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.client.utils.EmptyContent
import io.ktor.http.Headers
import io.ktor.http.Parameters
import io.ktor.http.encodedPath
import io.ktor.util.AttributeKey
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class LBKtorMonitoring private constructor(
    private val monitoring: LBMonitoring,
) {
    companion object : HttpClientPlugin<Config, LBKtorMonitoring> {
        override val key: AttributeKey<LBKtorMonitoring> = AttributeKey(name = "LBKtorMonitoringPlugin")

        private val RequestIdKey: AttributeKey<String> = AttributeKey(name = "RequestIdKey")
        private val RequestSendEpochMillisKey: AttributeKey<Long> = AttributeKey(name = "RequestSendEpochMillisKey")
        private val PrettyJson: Json = Json { prettyPrint = true }

        override fun prepare(block: Config.() -> Unit): LBKtorMonitoring {
            val config = Config().apply(block)
            return LBKtorMonitoring(
                monitoring = config.monitoring ?: throw IllegalArgumentException("Please provide a monitoring implementation"),
            )
        }

        @OptIn(ExperimentalUuidApi::class)
        override fun install(plugin: LBKtorMonitoring, scope: HttpClient) {
            scope.sendPipeline.intercept(HttpSendPipeline.State) {
                val requestId: String = Uuid.random().toHexString()
                context.attributes.put(RequestIdKey, requestId)
                context.attributes.put(RequestSendEpochMillisKey, Clock.System.now().toEpochMilliseconds())
            }
            scope.sendPipeline.intercept(HttpSendPipeline.Monitoring) {
                val requestBody = context.body.takeIf { it !is EmptyContent }?.toString()
                val headers = context.headers.build()
                plugin.monitoring.upsertRequest(
                    request = LBRequest(
                        id = context.attributes[RequestIdKey].let(Uuid::parseHex),
                        host = context.url.host,
                        methodName = context.method.value,
                        queryParams = context.url.encodedParameters.build().prettyfy(),
                        outgoingPayload = LBPayload(
                            headers = headers.prettyfy(),
                            body = try {
                                PrettyJson.encodeToString(PrettyJson.parseToJsonElement(requestBody.orEmpty()))
                            } catch (e: Exception) {
                                requestBody
                            },
                            size = (requestBody?.length?.toLong() ?: 0L) + headers.toString().length.toLong(),
                        ),
                        incomingPayload = null,
                        statusCode = -1,
                        sendingAt = context.attributes[RequestSendEpochMillisKey].let(Instant::fromEpochMilliseconds),
                        duration = 0.milliseconds,
                        path = context.url.encodedPath,
                    ),
                )
                scope.receivePipeline.intercept(HttpReceivePipeline.Before) { response ->
                    val requestId = response.request.attributes[RequestIdKey].let(Uuid::parseHex)
                    val request = plugin.monitoring.getRequestById(requestId = requestId)
                    val responseBody = response.bodyAsText()
                    val responseHeaders = response.headers
                    plugin.monitoring.upsertRequest(
                        request = request.copy(
                            incomingPayload = LBPayload(
                                headers = responseHeaders.prettyfy(),
                                body = try {
                                    PrettyJson.encodeToString(PrettyJson.parseToJsonElement(responseBody))
                                } catch (e: Exception) {
                                    responseBody
                                },
                                size = responseBody.length.toLong() + responseHeaders.toString().length.toLong(),
                            ),
                            statusCode = response.status.value,
                            duration = (Clock.System.now().toEpochMilliseconds() - response.request.attributes[RequestSendEpochMillisKey])
                                .milliseconds,
                        ),
                    )
                }
            }
        }
    }

    data class Config(
        var monitoring: LBMonitoring? = null,
    )
}

private fun Headers.prettyfy(): String {
    val headersAsPrettyString = mutableListOf<String>()
    forEach { key, params -> headersAsPrettyString += "$key=${params.joinToString()}" }
    return headersAsPrettyString.joinToString(separator = ";")
}

private fun Parameters.prettyfy(): String {
    val parametersAsPrettyString = mutableListOf<String>()
    forEach { key, params -> parametersAsPrettyString += "$key=${params.joinToString()}" }
    return parametersAsPrettyString.joinToString(separator = ";")
}
