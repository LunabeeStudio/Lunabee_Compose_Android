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

package studio.lunabee.core.model

import kotlin.concurrent.AtomicReference

actual class AtomicBoolean private constructor(private val atomicBoolean: AtomicReference<Boolean>) {
    actual constructor(initialValue: Boolean) : this(AtomicReference(initialValue))

    actual fun compareAndSet(expect: Boolean, update: Boolean): Boolean = atomicBoolean.compareAndSet(expect, update)

    actual val value: Boolean
        get() = atomicBoolean.value

    actual fun getAndSet(newValue: Boolean): Boolean = atomicBoolean.getAndSet(newValue)
}
