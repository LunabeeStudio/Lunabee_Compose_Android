package com.lunabee.lbcore.network

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
object NetworkUtils {
    private const val TYPE_NOT_CONNECTED = 0
    const val TYPE_WIFI: Int = 1
    const val TYPE_MOBILE: Int = 2

    fun getConnectivityStatus(context: Context): Int {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        return when (activeNetwork?.type) {
            ConnectivityManager.TYPE_WIFI -> {
                TYPE_WIFI
            }
            ConnectivityManager.TYPE_MOBILE -> {
                TYPE_MOBILE
            }
            else -> TYPE_NOT_CONNECTED
        }
    }

    fun isConnected(context: Context): Boolean = getConnectivityStatus(
        context,
    ) != TYPE_NOT_CONNECTED
}
