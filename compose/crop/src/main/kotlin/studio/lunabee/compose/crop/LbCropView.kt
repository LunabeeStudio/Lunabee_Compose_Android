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

package studio.lunabee.compose.crop

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.engawapg.lib.zoomable.zoomable

@Composable
fun LbCropView(
    state: LbCropViewState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    var repositioningJob: Job? = null

    if (state.readyToCompose) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(state.tempFile.toUri())
                .memoryCacheKey(state.key.toString())
                .build(),
            onState = {
                when (it) {
                    is AsyncImagePainter.State.Success -> {
                        coroutineScope.launch {
                            state.onImageReady(it.painter.intrinsicSize)
                        }
                    }

                    else -> {
                        // no-op
                    }
                }
            },
        )

        Image(
            painter = painter,
            modifier = modifier
                .zIndex(1f)
                .clipToBounds()
                .onGloballyPositioned {
                    state.setCropZoneSize(it.size.toSize())
                }.zoomable(
                    state.zoomState,
                    zoomEnabled = state.zoomState.scale <= state.maxScale * LbCropConst.MaxZoomSafeGard,
                ),
            contentDescription = null,
        )
    }

    LaunchedEffect(state.zoomState.scale) {
        if (state.readyToCompose) {
            repositioningJob?.cancel()
            if (state.zoomState.scale < state.minScale) {
                repositioningJob = coroutineScope.launch {
                    delay(LbCropConst.RepositionningDelayMs)
                    state.zoomState.changeScale(
                        targetScale = state.minScale,
                        position = Offset(state.zoomState.offsetX, state.zoomState.offsetY),
                    )
                }
            } else if (state.zoomState.scale > state.maxScale) {
                repositioningJob = coroutineScope.launch {
                    delay(LbCropConst.RepositionningDelayMs)
                    val scaleDiff = state.maxScale / state.zoomState.scale
                    state.zoomState.changeScale(
                        targetScale = state.maxScale,
                        position = Offset(state.zoomState.offsetX / scaleDiff, state.zoomState.offsetY / scaleDiff),
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            state.initState()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            state.clear()
        }
    }
}
