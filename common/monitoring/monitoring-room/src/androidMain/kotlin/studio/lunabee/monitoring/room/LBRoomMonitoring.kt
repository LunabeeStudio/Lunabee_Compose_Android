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
