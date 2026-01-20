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

package com.lunabee.lbextensions

import android.os.BaseBundle
import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun <T : Enum<T>> BaseBundle.putEnum(key: String, value: T) {
    putString(key, value.name)
}

inline fun <reified T : Enum<T>> BaseBundle.getEnum(key: String, defaultValue: T? = null): T? {
    return enumValueOfOrNull<T>(getString(key)) ?: defaultValue
}

inline fun <reified T : Parcelable> Bundle.getParcelableCompat(name: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(name, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(name)
    }
}

inline fun <reified T : java.io.Serializable> Bundle.getSerializableCompat(name: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(name, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializable(name) as? T
    }
}
