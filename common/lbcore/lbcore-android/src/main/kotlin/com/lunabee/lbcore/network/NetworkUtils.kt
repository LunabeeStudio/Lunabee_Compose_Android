package com.lunabee.lbcore.network

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
object NetworkUtils {
    private const val TypeNotConnected = 0
    const val TypeWifi: Int = 1
    const val TypeMobile: Int = 2

    fun getConnectivityStatus(context: Context): Int {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        return when (activeNetwork?.type) {
            ConnectivityManager.TYPE_WIFI -> {
                TypeWifi
            }
            ConnectivityManager.TYPE_MOBILE -> {
                TypeMobile
            }
            else -> TypeNotConnected
        }
    }

    fun isConnected(context: Context): Boolean = getConnectivityStatus(
        context,
    ) != TypeNotConnected
}
