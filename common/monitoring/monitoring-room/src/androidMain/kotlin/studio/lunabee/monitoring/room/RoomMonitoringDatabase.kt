@file:Suppress("MatchingDeclarationName", "Filename")

package studio.lunabee.monitoring.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.AndroidSQLiteDriver

internal actual class RoomPlatformBuilder(private val context: Context) {
    actual fun getDriver(): SQLiteDriver {
        return AndroidSQLiteDriver()
    }

    actual fun builder(): RoomDatabase.Builder<RoomMonitoringDatabase> {
        return Room.databaseBuilder<RoomMonitoringDatabase>(
            context = context,
            name = context.getDatabasePath(DatabaseName).absolutePath,
        )
    }
}
