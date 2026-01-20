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
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.AttributeKey
import kotlinx.serialization.json.Json

class LBKtorJson private constructor(
    private val url: String,
    private val prettyPrint: Boolean,
    private val isLenient: Boolean,
    private val ignoreUnknownKeys: Boolean,
    private val explicitNulls: Boolean,
) {
    companion object : HttpClientPlugin<Config, LBKtorJson> {
        override val key: AttributeKey<LBKtorJson> = AttributeKey(name = "LBKtorJsonPlugin")

        override fun prepare(block: Config.() -> Unit): LBKtorJson {
            val config = Config().apply(block)
            return LBKtorJson(
                url = config.url ?: throw IllegalArgumentException("url parameter not set"),
                prettyPrint = config.prettyPrint,
                isLenient = config.isLenient,
                ignoreUnknownKeys = config.ignoreUnknownKeys,
                explicitNulls = config.explicitNulls,
            )
        }

        override fun install(plugin: LBKtorJson, scope: HttpClient) {
            ContentNegotiation.install(
                plugin = ContentNegotiation.prepare {
                    json(
                        Json {
                            this.prettyPrint = plugin.prettyPrint
                            this.isLenient = plugin.isLenient
                            this.ignoreUnknownKeys = plugin.ignoreUnknownKeys
                            this.explicitNulls = plugin.explicitNulls
                        },
                    )
                },
                scope = scope,
            )

            DefaultRequest.install(
                plugin = DefaultRequest.prepare {
                    url(urlString = plugin.url)
                    accept(contentType = ContentType.Application.Json)
                    contentType(type = ContentType.Application.Json)
                },
                scope = scope,
            )
        }
    }

    data class Config(
        var url: String? = null,
        var prettyPrint: Boolean = true,
        var isLenient: Boolean = true,
        var ignoreUnknownKeys: Boolean = true,
        var explicitNulls: Boolean = false,
    )
}
