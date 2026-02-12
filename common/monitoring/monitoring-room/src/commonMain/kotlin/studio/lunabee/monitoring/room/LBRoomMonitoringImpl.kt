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

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import studio.lunabee.monitoring.core.LBMonitoring
import studio.lunabee.monitoring.core.LBRequest
import studio.lunabee.monitoring.room.dao.RoomRequestDao
import studio.lunabee.monitoring.room.di.RoomMonitoringKoinProvider
import studio.lunabee.monitoring.room.entity.RoomRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.Uuid

internal class LBRoomMonitoringImpl private constructor() {
    internal companion object : LBMonitoring {
        private val requestDao: RoomRequestDao by lazy { RoomMonitoringKoinProvider.requestDao }

        override fun getPagingRequests(): Flow<PagingData<LBRequest>> {
            return Pager(
                config = PagingConfig(pageSize = 30),
                pagingSourceFactory = { requestDao.getPagingRequests() },
            ).flow.map { pagingData -> pagingData.map(RoomRequest::toModel) }
        }

        override fun getRequestsPaginatedAsFlow(pageSize: Int, skip: Int): Flow<List<LBRequest>> {
            return requestDao.getRequestsPaginatedAsFlow(pageSize = pageSize, skip = skip)
                .map { requests -> requests.map(RoomRequest::toModel) }
        }

        override fun getRequestsPaginatedAsFlow(): Flow<List<LBRequest>> {
            return requestDao.getRequestsPaginatedAsFlow().map { requests -> requests.map(RoomRequest::toModel) }
        }

        override suspend fun getRequestById(requestId: Uuid): LBRequest {
            return requestDao.getRequestById(requestId = requestId).toModel()
        }

        override suspend fun upsertRequest(request: LBRequest) {
            requestDao.upsertRequest(request = RoomRequest.fromModel(model = request))
        }

        override fun getRequestByIdAsFlow(requestId: Uuid): Flow<LBRequest> {
            return requestDao.getRequestByIdAsFlow(requestId = requestId).map(RoomRequest::toModel)
        }

        override suspend fun flush() {
            requestDao.clear()
        }

        override suspend fun removeRequestById(requestId: Uuid) {
            requestDao.removeRequestById(requestId = requestId)
        }

        override fun getRequestCountAsFlow(): Flow<Int> {
            return requestDao.getRequestCountAsFlow()
        }

        override suspend fun getRequestsPaginated(pageSize: Int, skip: Int): List<LBRequest> {
            return requestDao.getRequestsPaginated(pageSize = pageSize, skip = skip).map(RoomRequest::toModel)
        }

        override suspend fun getRequestCount(): Int {
            return requestDao.getRequestCount()
        }
    }
}
