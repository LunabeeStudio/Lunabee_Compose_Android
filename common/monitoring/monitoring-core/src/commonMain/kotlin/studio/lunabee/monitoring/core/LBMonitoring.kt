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

package studio.lunabee.monitoring.core

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

/**
 * Implements this interface to provide a local access to data stored by the network monitoring plugin used (ex: lbmonitoring-ktor).
 * Our libraries provides a Room implementation of this interface. (see lbmonitoring-room)
 */
interface LBMonitoring {
    /**
     * Get paginated requests, order by date descending.
     * @return a [Flow] of [PagingData] that contains [LBRequest] objects.
     */
    fun getPagingRequests(): Flow<PagingData<LBRequest>>

    /**
     * Similar to [getPagingRequests] but you will have to implements your own pagination system.
     * It can be useful for any app not using [PagingData].
     */
    fun getRequestsPaginatedAsFlow(pageSize: Int, skip: Int): Flow<List<LBRequest>>

    fun getRequestsPaginatedAsFlow(): Flow<List<LBRequest>>

    suspend fun getRequestsPaginated(pageSize: Int, skip: Int): List<LBRequest>

    /**
     * Get a specific observable request by its id to get more details.
     * @param requestId id of the request to get as [Uuid].
     * @return a [Flow] with the [LBRequest].
     */
    fun getRequestByIdAsFlow(requestId: Uuid): Flow<LBRequest>

    /**
     * Get a specific request by its id to get more details.
     * @param requestId id of the request to get as [Uuid].
     * @return a [LBRequest].
     */
    suspend fun getRequestById(requestId: Uuid): LBRequest

    /**
     * Insert or update a request into the database.
     * @param request request to upsert.
     */
    suspend fun upsertRequest(request: LBRequest)

    /**
     * Delete all stored requests
     */
    suspend fun flush()

    /**
     * Delete a request by its id
     * @param requestId id of the request to delete
     */
    suspend fun removeRequestById(requestId: Uuid)

    /**
     * @return the number of requests stored
     */
    fun getRequestCountAsFlow(): Flow<Int>

    suspend fun getRequestCount(): Int
}
