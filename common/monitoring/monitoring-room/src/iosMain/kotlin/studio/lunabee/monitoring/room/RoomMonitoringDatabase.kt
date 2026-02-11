@file:Suppress("MatchingDeclarationName", "Filename")

package studio.lunabee.monitoring.room

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.NativeSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal actual class RoomPlatformBuilder {
    actual fun getDriver(): SQLiteDriver {
        return NativeSQLiteDriver()
    }

    actual fun builder(): RoomDatabase.Builder<RoomMonitoringDatabase> {
        val dbFilePath = "${fileDirectory()}/$DatabaseName"
        return Room.databaseBuilder<RoomMonitoringDatabase>(name = dbFilePath)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}
