package com.lunabee.lbextensions

import co.touchlab.kermit.Logger
import com.lunabee.lblogger.LBLogger

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String?, logger: Logger? = EnumExt.enumLogger): T? = try {
    name?.let { enumValueOf<T>(it) }
} catch (e: IllegalArgumentException) {
    logger?.e("Failed to get enum ${T::class.simpleName} for string \"$name\"")
    null
}

object EnumExt {
    val enumLogger: Logger = LBLogger.get("EnumExt")
}
