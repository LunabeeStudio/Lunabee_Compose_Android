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
