package studio.lunabee.monitoring.room

import androidx.room.TypeConverter
import kotlin.uuid.Uuid

class UuidConverter {
    @TypeConverter
    fun uuidToBytes(uuid: Uuid?): ByteArray? {
        return uuid?.toByteArray()
    }

    @TypeConverter
    fun bytesToUuid(bytes: ByteArray?): Uuid? {
        return bytes?.let { Uuid.fromByteArray(it) }
    }
}
