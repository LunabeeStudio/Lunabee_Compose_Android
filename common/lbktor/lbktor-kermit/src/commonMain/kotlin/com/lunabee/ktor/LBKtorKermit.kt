package com.lunabee.ktor

import com.lunabee.lblogger.LBLogger
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
