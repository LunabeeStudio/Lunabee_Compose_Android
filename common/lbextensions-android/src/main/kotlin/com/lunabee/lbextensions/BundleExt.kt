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
