package studio.lunabee.monitoring.ui

import studio.lunabee.monitoring.core.LBMonitoring
import studio.lunabee.monitoring.ui.di.UiMonitoringIsolatedContext
import org.koin.dsl.module

object LBUiMonitoring {
    fun init(monitoring: LBMonitoring) {
        UiMonitoringIsolatedContext.init {
            modules(
                module {
                    single<LBMonitoring> { monitoring }
                },
            )
        }
    }
}
