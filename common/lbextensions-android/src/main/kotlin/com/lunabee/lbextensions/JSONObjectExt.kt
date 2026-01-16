@file:Suppress("unused")
@file:JvmName("JsonUtils")

package com.lunabee.lbextensions

import org.json.JSONObject

/**
 * Returns the value mapped by [name] if it exists, coercing it if
 * necessary, or [fallback] if no such mapping exists or the value of [name] is "null".
 */
fun JSONObject.optStringFixed(name: String, fallback: String): String {
    return if (isNull(name)) {
        return fallback
    } else {
        optString(name, fallback)
    }
}

/**
 * Returns the value mapped by [name] if it exists, coercing it if
 * necessary, or the empty string if no such mapping exists or the value of [name] is "null".
 */
fun JSONObject.optStringFixed(name: String): String? {
    return if (isNull(name)) {
        null
    } else {
        optString(name)
    }
}
