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

package studio.lunabee.monitoring.ui

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import studio.lunabee.monitoring.core.LBMonitoring
import platform.UIKit.UIViewController

@Suppress("unused")
object LBMonitoringMainController {
    fun get(
        monitoring: LBMonitoring,
        closeMonitoring: () -> Unit,
    ): UIViewController {
        studio.lunabee.monitoring.ui.LBUiMonitoring.init(monitoring = monitoring)
        return ComposeUIViewController(
            configure = {
                onFocusBehavior = OnFocusBehavior.DoNothing // Let Compose handle keyboard instead of iOS.
            },
        ) {
            studio.lunabee.monitoring.ui.LBMonitoringMainRoute(
                closeMonitoring = closeMonitoring,
            )
        }
    }
}
