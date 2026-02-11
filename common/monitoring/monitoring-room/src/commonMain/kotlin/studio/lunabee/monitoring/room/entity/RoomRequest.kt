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

package studio.lunabee.monitoring.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.lunabee.monitoring.core.LBPayload
import studio.lunabee.monitoring.core.LBRequest
import kotlin.time.Instant
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.Uuid

@Entity
data class RoomRequest(
    @PrimaryKey
    val id: Uuid,
    val host: String,
    val methodName: String,
    val statusCode: Int,
    val sendingAt: Long,
    val duration: Long,
    val path: String?,
    val queryParams: String?,
    val outgoingHeaders: String?,
    val outgoingBody: String?,
    val outgoingPayloadSize: Long,
    val incomingHeaders: String?,
    val incomingBody: String?,
    val incomingPayloadSize: Long,
) {
    internal fun toModel(): LBRequest {
        return LBRequest(
            id = id,
            host = host,
            methodName = methodName,
            statusCode = statusCode,
            sendingAt = Instant.fromEpochMilliseconds(sendingAt),
            duration = duration.milliseconds,
            path = path,
            queryParams = queryParams,
            outgoingPayload = LBPayload(
                headers = outgoingHeaders,
                body = outgoingBody,
                size = outgoingPayloadSize,
            ),
            incomingPayload = LBPayload(
                headers = incomingHeaders,
                body = incomingBody,
                size = incomingPayloadSize,
            ),
        )
    }

    companion object {
        internal fun fromModel(model: LBRequest): RoomRequest {
            return RoomRequest(
                id = model.id,
                host = model.host,
                methodName = model.methodName,
                statusCode = model.statusCode,
                sendingAt = model.sendingAt.toEpochMilliseconds(),
                duration = model.duration.inWholeMilliseconds,
                path = model.path,
                queryParams = model.queryParams,
                outgoingHeaders = model.outgoingPayload?.headers,
                outgoingBody = model.outgoingPayload?.body,
                outgoingPayloadSize = model.outgoingPayload?.size ?: 0,
                incomingHeaders = model.incomingPayload?.headers,
                incomingBody = model.incomingPayload?.body,
                incomingPayloadSize = model.incomingPayload?.size ?: 0,
            )
        }
    }
}
