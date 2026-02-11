package studio.lunabee.monitoring.ui.di

import studio.lunabee.monitoring.ui.details.NetworkRequestDetailViewModel
import studio.lunabee.monitoring.ui.list.NetworkRequestListViewModel
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

internal object UiMonitoringIsolatedContext {
    private var koinApp: KoinApplication? = null

    val koin: Koin
        get() = getSafeKoinApp().koin

    fun getSafeKoinApp(): KoinApplication {
        return checkNotNull(koinApp) { "Did you call start()?" }
    }

    fun init(block: KoinApplication.() -> Unit) {
        koinApp = koinApplication {
            block()
            modules(
                module {
                    viewModelOf(::NetworkRequestListViewModel)
                    viewModelOf(::NetworkRequestDetailViewModel)
                },
            )
        }
    }
}
