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

import studio.lunabee.logger.LBLogger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.util.AttributeKey

class LBKtorKermit private constructor(
    private val logLevel: LogLevel,
) {
    companion object : HttpClientPlugin<Config, LBKtorKermit> {
        override val key: AttributeKey<LBKtorKermit> = AttributeKey(name = "LBKtorKermitPlugin")

        override fun prepare(block: Config.() -> Unit): LBKtorKermit {
            val config = Config().apply(block)
            return LBKtorKermit(config.logLevel)
        }

        override fun install(plugin: LBKtorKermit, scope: HttpClient) {
            Logging.install(
                plugin = Logging.prepare {
                    this.level = plugin.logLevel.toKtorLogLevel()
                    this.logger = object : Logger {
                        val delegate = LBLogger.get(name = "LBKtorKermitDefault")

                        override fun log(message: String): Unit = delegate.d(messageString = message)
                    }
                },
                scope = scope,
            )
        }
    }

    data class Config(
        var logLevel: LogLevel = LogLevel.All,
    )

    enum class LogLevel {
        All,
        Headers,
        Body,
        Info,
        None,
        ;

        internal fun toKtorLogLevel(): io.ktor.client.plugins.logging.LogLevel = io.ktor.client.plugins.logging.LogLevel
            .valueOf(name.uppercase())
    }
}
