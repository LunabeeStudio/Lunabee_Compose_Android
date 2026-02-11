package studio.lunabee.monitoring.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.SQLiteDriver
import studio.lunabee.monitoring.room.dao.RoomRequestDao
import studio.lunabee.monitoring.room.entity.RoomRequest
import kotlinx.coroutines.CoroutineDispatcher

@TypeConverters(
    UuidConverter::class,
)
@Database(
    entities = [
        RoomRequest::class,
    ],
    version = 1,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class RoomMonitoringDatabase : RoomDatabase() {
    internal abstract fun requestDao(): RoomRequestDao
}

internal expect class RoomPlatformBuilder {
    fun builder(): RoomDatabase.Builder<RoomMonitoringDatabase>

    fun getDriver(): SQLiteDriver
}

/**
 * Automatically generated actual implementation.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<RoomMonitoringDatabase> {
    // Needed as compilation error might randomly occurred (i.e with Dokka for example).
    override fun initialize(): RoomMonitoringDatabase
}

internal const val DatabaseName: String = "studio.lunabee.monitoring.db"

internal fun getRoomDb(builder: RoomPlatformBuilder, dispatcher: CoroutineDispatcher): RoomMonitoringDatabase {
    return builder
        .builder()
        // Here goes manual migration if needed.
        .setDriver(builder.getDriver())
        .setQueryCoroutineContext(dispatcher)
        .build()
}
