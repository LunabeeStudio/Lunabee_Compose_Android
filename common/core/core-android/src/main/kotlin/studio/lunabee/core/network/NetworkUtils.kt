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

package studio.lunabee.core.network

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
