package com.lunabee.lbextensions.content

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
