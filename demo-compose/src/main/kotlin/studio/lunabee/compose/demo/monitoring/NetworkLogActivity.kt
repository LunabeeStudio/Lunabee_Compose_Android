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

package studio.lunabee.compose.demo.monitoring

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import studio.lunabee.monitoring.ui.LBMonitoringMainRoute
import studio.lunabee.monitoring.ui.LBUiMonitoring

class NetworkLogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        LBUiMonitoring.init(monitoring = LBMonitoringDemo.monitoring)
        setContent {
            LBMonitoringMainRoute(
                closeMonitoring = ::finish,
            )
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, NetworkLogActivity::class.java))
        }
    }
}
