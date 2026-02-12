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

/**
 * Data class that represents a request or a response payload.
 * @param headers headers of the request or response, represented as a string.
 * @param body body of the request or response, represented as a string.
 * @param size size of the request or response in bytes (headers size + body size).
 */
data class LBPayload(
    val headers: String? = null,
    val body: String? = null,
    val size: Long = 0,
)
