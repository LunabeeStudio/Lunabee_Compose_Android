package studio.lunabee.monitoring.room

import studio.lunabee.monitoring.core.LBMonitoring
import studio.lunabee.monitoring.room.di.RoomMonitoringIsolatedContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

/**
 * This object allows any user to get a [LBMonitoring] implementation using a Room database, without any extra code.
 */
object LBRoomMonitoring {
    fun get(): LBMonitoring {
        RoomMonitoringIsolatedContext.init {
            modules(
                module {
                    single { Dispatchers.IO }
                    single { RoomPlatformBuilder() }
                },
            )
        }
        return LBRoomMonitoringImpl
    }
}
