package studio.lunabee.monitoring.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import studio.lunabee.monitoring.room.entity.RoomRequest
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
internal interface RoomRequestDao {
    @Query("SELECT * FROM RoomRequest ORDER BY sendingAt DESC")
    fun getPagingRequests(): PagingSource<Int, RoomRequest>

    @Query("SELECT * FROM RoomRequest ORDER BY sendingAt DESC LIMIT :pageSize OFFSET :skip")
    fun getRequestsPaginatedAsFlow(pageSize: Int, skip: Int): Flow<List<RoomRequest>>

    @Query("SELECT * FROM RoomRequest ORDER BY sendingAt")
    fun getRequestsPaginatedAsFlow(): Flow<List<RoomRequest>>

    @Query("SELECT * FROM RoomRequest ORDER BY sendingAt DESC LIMIT :pageSize OFFSET :skip")
    suspend fun getRequestsPaginated(pageSize: Int, skip: Int): List<RoomRequest>

    @Query("SELECT * FROM RoomRequest WHERE id = :requestId")
    suspend fun getRequestById(requestId: Uuid): RoomRequest

    @Upsert
    suspend fun upsertRequest(request: RoomRequest)

    @Query("SELECT * FROM RoomRequest ORDER BY sendingAt DESC LIMIT 1")
    suspend fun getLastRequest(): RoomRequest?

    @Query("SELECT * FROM RoomRequest WHERE id = :requestId")
    fun getRequestByIdAsFlow(requestId: Uuid): Flow<RoomRequest>

    @Query("DELETE FROM RoomRequest")
    suspend fun clear()

    @Query("DELETE FROM RoomRequest WHERE id = :requestId")
    suspend fun removeRequestById(requestId: Uuid)

    @Query("SELECT COUNT(*) FROM RoomRequest")
    fun getRequestCountAsFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM RoomRequest")
    suspend fun getRequestCount(): Int
}
