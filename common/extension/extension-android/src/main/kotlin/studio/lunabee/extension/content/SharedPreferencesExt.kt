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

package studio.lunabee.extension.content

import android.content.SharedPreferences
import android.util.Base64

/**
 * Retrieve [ByteArray] in shared preferences stored as base64 string
 */
fun SharedPreferences.getData(key: String, defValue: ByteArray?): ByteArray? =
    getString(key, null)?.let {
        Base64.decode(it, Base64.NO_WRAP)
    } ?: defValue

/**
 * Store [ByteArray] in shared preferences as base64 string
 */
fun SharedPreferences.Editor.putData(key: String, data: ByteArray?) {
    putString(key, data?.let { Base64.encodeToString(data, Base64.NO_WRAP) })
}
