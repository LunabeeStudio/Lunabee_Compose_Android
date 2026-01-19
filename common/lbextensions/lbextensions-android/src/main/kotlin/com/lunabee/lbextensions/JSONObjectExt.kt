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
