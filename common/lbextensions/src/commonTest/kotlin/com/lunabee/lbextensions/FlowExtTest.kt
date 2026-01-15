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

package com.lunabee.lbextensions

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class FlowExtTest {
    @Test
    fun `Collect flow withPreviousState test`(): TestResult = runTest {
        val flow = flowOf(null, 1, 2, null, 3)
        val expected = listOf(
            null to null,
            null to 1,
            1 to 2,
            2 to null,
            null to 3,
        )
        val actual = flow.withPreviousState().toList()
        assertContentEquals(expected, actual)
    }
}
