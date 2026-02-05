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

package studio.lunabee.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpCallValidatorConfig
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.api.ClientPluginInstance
import io.ktor.client.plugins.plugin
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import io.ktor.util.AttributeKey
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

sealed class LBKtorException(cause: Throwable?) : Throwable(message = cause?.message, cause = cause) {
    class Decoding(cause: Throwable) : LBKtorException(cause = cause)

    class IO(cause: Throwable) : LBKtorException(cause = cause)

    class ServerResponse(val response: HttpResponse) : LBKtorException(cause = null)

    class Unexpected(cause: Throwable) : LBKtorException(cause = cause)
}

class LBKtorExceptionHandler private constructor(
    private val mapErr: suspend (e: LBKtorException) -> Throwable,
) {
    companion object : HttpClientPlugin<Config, LBKtorExceptionHandler> {
        override val key: AttributeKey<LBKtorExceptionHandler> = AttributeKey(name = "LBKtorExceptionHandler")

        override fun prepare(block: Config.() -> Unit): LBKtorExceptionHandler {
            val config = Config().apply(block)
            return LBKtorExceptionHandler(
                mapErr = config.mapErr,
            )
        }

        override fun install(plugin: LBKtorExceptionHandler, scope: HttpClient) {
            // When the request is execute. Only catch [IOException] here. If you have a serialization exception when the request is
            // sent, this is a development error, so we let the error be thrown.
            scope.plugin(HttpSend).intercept { request ->
                try {
                    execute(request)
                } catch (e: IOException) {
                    // i.e response reading failed (i.e no internet, no hostname...)
                    throw plugin.mapErr(LBKtorException.IO(cause = e))
                }
            }

            // When the response is received.
            HttpCallValidator.install(
                plugin = buildPlugin(plugin),
                scope = scope,
            )
        }

        @Suppress("ThrowsCount")
        private fun buildPlugin(
            plugin: LBKtorExceptionHandler,
        ): ClientPluginInstance<HttpCallValidatorConfig> = HttpCallValidator.prepare {
            handleResponseExceptionWithRequest { exception, _ ->
                // Check cause that might be wrapped before the exception itself.
                // This block is called even if HttpSend throw an exception, and the initial error might have been wrapped
                // by the caller.
                when (exception.cause ?: exception) {
                    is CancellationException,
                    is kotlin.coroutines.cancellation.CancellationException,
                    is LBKtorException,
                    -> throw exception

                    is ContentConvertException,
                    is NoTransformationFoundException,
                    is SerializationException,
                    -> throw plugin.mapErr(LBKtorException.Decoding(cause = exception))

                    is IOException -> throw plugin.mapErr(LBKtorException.IO(cause = exception))

                    else -> throw plugin.mapErr(LBKtorException.Unexpected(cause = exception))
                }
            }

            // In case of error with back-end, wrap the error in a [LBKtorException]. We do that to avoid deserialization error
            // if the message return is not a valid object.
            validateResponse { response: HttpResponse ->
                if (!response.status.isSuccess()) {
                    throw plugin.mapErr(LBKtorException.ServerResponse(response = response))
                }
            }
        }
    }

    data class Config(
        var mapErr: suspend (e: LBKtorException) -> Throwable = { it },
    )
}
