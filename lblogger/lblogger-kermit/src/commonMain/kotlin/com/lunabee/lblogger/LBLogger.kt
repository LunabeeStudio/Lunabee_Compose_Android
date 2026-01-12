package com.lunabee.lblogger

import co.touchlab.kermit.Logger

object LBLogger {
    /**
     * Get or create a [Logger] with name inferred from type [T]
     *
     * @param short Use simple class name instead of fully qualified name as logger name
     */
    inline fun <reified T : Any> get(short: Boolean = true): Logger {
        val tag = if (short) T::class.simpleName else null
        return get(tag ?: T::class.qualifiedName ?: "")
    }

    /**
     * Get or create a [Logger] with name [name]
     */
    fun get(name: String): Logger = Logger.withTag(name)
}
