package studio.lunabee.monitoring.room.di

import studio.lunabee.monitoring.room.RoomMonitoringDatabase
import studio.lunabee.monitoring.room.dao.RoomRequestDao
import studio.lunabee.monitoring.room.getRoomDb
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.dsl.koinApplication
import org.koin.dsl.module

internal object RoomMonitoringKoinProvider : RoomMonitoringIsolatedComponent {
    val requestDao: RoomRequestDao
        get() = getKoin().get()
}

internal object RoomMonitoringIsolatedContext {
    private var koinApp: KoinApplication? = null

    val koin: Koin
        get() = koinApp?.koin ?: throw IllegalArgumentException("KoinApp not initialized")

    fun init(block: KoinApplication.() -> Unit) {
        koinApp = koinApplication {
            block()
            modules(
                module {
                    single { getRoomDb(builder = get(), dispatcher = get()) }
                    single { get<RoomMonitoringDatabase>().requestDao() }
                },
            )
        }
    }
}

internal interface RoomMonitoringIsolatedComponent : KoinComponent {
    override fun getKoin(): Koin = RoomMonitoringIsolatedContext.koin
}
