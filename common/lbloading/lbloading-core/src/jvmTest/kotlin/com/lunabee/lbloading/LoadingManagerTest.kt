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

package com.lunabee.lbloading

import com.lunabee.lbcore.model.LBFlowResult
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoadingManagerTest {

    private val rawLoadingManager = SimpleLoadingManager()
    private val loadingManager = spyk(rawLoadingManager)

    @Test
    fun flow_withLoading_no_loading_test(): TestResult = runTest {
        emptyFlow<LBFlowResult<Unit>>()
            .withLoading(loadingManager)
            .collect()

        verify(exactly = 0) { loadingManager.startLoading() }
        verify(exactly = 1) { loadingManager.stopLoading() }
    }

    @Test
    fun flow_withLoading_multi_loading_test(): TestResult = runTest {
        flow {
            emit(LBFlowResult.Loading(Unit))
            emit(LBFlowResult.Loading(Unit)) // already loading
            rawLoadingManager.stopLoading() // external call to stop, do not record
            emit(LBFlowResult.Loading(Unit))
        }.withLoading(loadingManager)
            .collect()

        verify(exactly = 2) { loadingManager.startLoading() }
        verify(exactly = 1) { loadingManager.stopLoading() }
    }

    @Test
    fun flow_withLoading_stream_crash_test(): TestResult = runTest {
        assertThrows<IllegalStateException> {
            flow {
                emit(LBFlowResult.Loading(Unit))
                error("Flow failure")
            }.withLoading(loadingManager)
                .collect()
        }

        verify(exactly = 1) { loadingManager.startLoading() }
        verify(exactly = 1) { loadingManager.stopLoading() }
    }

    @Test
    fun flow_withLoading_collect_crash_test(): TestResult = runTest {
        assertThrows<IllegalStateException> {
            flow {
                emit(LBFlowResult.Loading(Unit))
            }.withLoading(loadingManager)
                .collect {
                    error("collect failure")
                }
        }

        verify(exactly = 1) { loadingManager.startLoading() }
        verify(exactly = 1) { loadingManager.stopLoading() }
    }

    @Test
    fun flow_withLoading_cancel_test(): TestResult = runTest {
        val job = flow {
            emit(LBFlowResult.Loading(Unit))
            while (true) yield()
        }.withLoading(loadingManager)
            .launchIn(backgroundScope)

        yield()
        job.cancel()
        yield()

        verify(exactly = 1) { loadingManager.startLoading() }
        verify(exactly = 1) { loadingManager.stopLoading() }
    }
}
