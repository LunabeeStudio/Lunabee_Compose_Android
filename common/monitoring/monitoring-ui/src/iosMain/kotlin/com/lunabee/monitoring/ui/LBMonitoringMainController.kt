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
        LBUiMonitoring.init(monitoring = monitoring)
        return ComposeUIViewController(
            configure = {
                onFocusBehavior = OnFocusBehavior.DoNothing // Let Compose handle keyboard instead of iOS.
            },
        ) {
            LBMonitoringMainRoute(
                closeMonitoring = closeMonitoring,
            )
        }
    }
}
