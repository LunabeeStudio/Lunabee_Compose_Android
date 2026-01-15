package com.lunabee.lblogger

import co.touchlab.crashkios.crashlytics.CrashlyticsCalls
import co.touchlab.crashkios.crashlytics.CrashlyticsCallsActual
import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import kotlin.reflect.KClass

class LBCrashlyticsLogWriter(
    private val exceptionListToIgnore: List<KClass<out Throwable>?> = emptyList(),
    private val shouldLog: (severity: Severity, tag: String?, message: String, t: Throwable?) -> Boolean = { _, _, _, _ -> true },
    private val recordErrorMessage: Boolean = true,
    private val messageStringFormatter: MessageStringFormatter = DefaultFormatter,
) : LogWriter() {
    private val crashlyticsCalls: CrashlyticsCalls = CrashlyticsCallsActual()

    init {
        enableCrashlytics()
    }

    private fun isLoggable(severity: Severity, throwable: Throwable?, tag: String?, message: String): Boolean {
        if (throwable != null && exceptionListToIgnore.contains(throwable::class)) {
            return false
        }

        return shouldLog(severity, tag, message, throwable)
    }

    override fun isLoggable(tag: String, severity: Severity): Boolean {
        return severity > Severity.Debug
    }

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        if (!isLoggable(severity, throwable, tag, message)) return

        val formatedMessage = messageStringFormatter.formatMessage(
            severity = severity,
            tag = Tag(tag),
            message = Message(message.takeIf { it.isNotBlank() } ?: "no message"),
        )

        when {
            // Log with exception -> send exception wrapped in a LogWrapperException to add some context (severity, tag)
            throwable != null ->
                crashlyticsCalls.sendHandledException(LogWrapperException(formatedMessage, throwable))
            // Error log without exception -> create a LogWrapperException if recordErrorMessage enabled
            severity >= Severity.Error && recordErrorMessage ->
                crashlyticsCalls.sendHandledException(LogWrapperException(formatedMessage, null))
            // Fallback default log
            else ->
                crashlyticsCalls.logMessage(formatedMessage)
        }
    }
}

private class LogWrapperException(message: String, cause: Throwable?) : Exception(message, cause)
