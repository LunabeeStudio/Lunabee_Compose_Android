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

package studio.lunabee.monitoring.room

import android.content.Context
import studio.lunabee.monitoring.core.LBMonitoring
import studio.lunabee.monitoring.room.di.RoomMonitoringIsolatedContext
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * This object allows any user to get a [LBMonitoring] implementation using a Room database, without any extra code.
 */
object LBRoomMonitoring {
    fun get(
        context: Context,
        dispatcher: CoroutineDispatcher,
    ): LBMonitoring {
        RoomMonitoringIsolatedContext.init {
            androidContext(androidContext = context)
            modules(
                module {
                    single { dispatcher }
                    single { RoomPlatformBuilder(context = get()) }
                },
            )
        }
        return LBRoomMonitoringImpl
    }
}
