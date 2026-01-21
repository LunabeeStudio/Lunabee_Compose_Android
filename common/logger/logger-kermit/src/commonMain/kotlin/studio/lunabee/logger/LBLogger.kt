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

package studio.lunabee.logger

import co.touchlab.kermit.Logger

object LBLogger {
    /**
     * Get or create a [Logger] with name inferred from type [T]
     *
     * @param short Use simple class name instead of fully qualified name as logger name
     */
    inline fun <reified T : Any> get(short: Boolean = true): Logger {
        val tag = if (short) T::class.simpleName else null
        return get(tag ?: T::class.qualifiedName ?: "")
    }

    /**
     * Get or create a [Logger] with name [name]
     */
    fun get(name: String): Logger = Logger.withTag(name)
}
