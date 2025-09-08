/*
 * Copyright (c) 2024 Lunabee Studio
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
 *
 * LbCropViewState.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/24/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbccrop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import net.engawapg.lib.zoomable.ZoomState
import net.engawapg.lib.zoomable.rememberZoomState
import java.io.File
import java.util.UUID
import kotlin.math.min

@Stable
class LbCropViewState(
    private val originalBitmap: Bitmap,
    private val originalOrientation: Int,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    val zoomState: ZoomState,
    context: Context,
    private val finalImageMinSize: CropImageSize = CropImageSize.Zero
) {
    val tempFile: File = File(context.cacheDir, LbCropConst.TempFile)

    private val cropUtils = LbCropUtils(coroutineDispatcher)

    var readyToCompose: Boolean by mutableStateOf(false)

    private var cropZoneHeightPx: Float by mutableFloatStateOf(1f)
    private var cropZoneWidthPx: Float by mutableFloatStateOf(1f)

    var minScale: Float by mutableFloatStateOf(1f)
    var maxScale: Float by mutableFloatStateOf(1f)

    // Used to force a recomposition of the image.
    var key: UUID by mutableStateOf(UUID.randomUUID())

    /**
     * Will init the LbCropViewState.
     * It will save the original bitmap into a temp file so that it will be easier to work with.
     */
    suspend fun initState() {
        if (originalBitmap.width == 0 || originalBitmap.height == 0) {
            throw LbCropNullImageThrowable()
        } else {
            val rotatedBitmap =
                when (originalOrientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> {
                        cropUtils.rotateImage(
                            bitmap = originalBitmap,
                            rotationAngle = 90f
                        )
                    }
                    ExifInterface.ORIENTATION_ROTATE_180 -> {
                        cropUtils.rotateImage(
                            bitmap = originalBitmap,
                            rotationAngle = 180f
                        )
                    }
                    ExifInterface.ORIENTATION_ROTATE_270 -> {
                        cropUtils.rotateImage(
                            bitmap = originalBitmap,
                            rotationAngle = 270f
                        )
                    }
                    else -> originalBitmap
                }
            cropUtils.saveBitmapInFile(
                bitmap = rotatedBitmap,
                destinationFile = tempFile,
                onSuccess = {
                    readyToCompose = true
                }
            )
        }
    }

    internal fun setCropZoneSize(size: Size) {
        zoomState.setLayoutSize(size)
        cropZoneWidthPx = size.width
        cropZoneHeightPx = size.height
    }

    internal suspend fun onImageReady(imageSize: Size) {
        val imageWidthPx = imageSize.width
        val imageHeightPx = imageSize.height
        val isImageVertical = imageHeightPx > imageWidthPx

        // Set the content size so that zoomState "knows" the size of the "fitted" image.
        zoomState.setContentSize(imageSize)

        // Image fit the crop zone in only one dimens, we need to find the zoom level so that the image fit in both dimension
        val imageRatio: Float = imageWidthPx / imageHeightPx
        minScale =
            if (isImageVertical) {
                // In that case, the image initially fits the crop zone height.
                val currentHeight = cropZoneHeightPx
                val currentWidth = currentHeight * imageRatio
                cropZoneWidthPx / currentWidth
            } else {
                // In that case, the image initially fits the crop zone width.
                val currentWidth = cropZoneWidthPx // Image already fit width
                val currentHeight = currentWidth / imageRatio
                cropZoneHeightPx / currentHeight
            }

        computeMaxScale()

        zoomState.changeScale(
            targetScale = minScale,
            position = Offset(0f, 0f)
        )
    }

    private fun computeMaxScale() {
        val widthScale: Float = (cropZoneWidthPx * minScale) / finalImageMinSize.width
        val heightScale: Float = (cropZoneHeightPx * minScale) / finalImageMinSize.height
        maxScale = min(widthScale, heightScale).coerceAtLeast(1f)
    }

    suspend fun crop(): Bitmap {
        val bitmap =
            BitmapFactory.decodeFile(
                tempFile.path
            )
        val cropQuery =
            CropImageQuery(
                originalBitmap = bitmap,
                offsetX = zoomState.offsetX,
                offsetY = zoomState.offsetY,
                width = cropZoneWidthPx,
                height = cropZoneHeightPx,
                scale = zoomState.scale,
                originalScale = minScale
            )
        return cropUtils.cropImage(cropQuery)
    }

    suspend fun rotate() {
        val rotatedBitmap =
            cropUtils.rotateImage(
                fileUri = tempFile.toUri(),
                rotationAngle = LbCropConst.RotationAngleIncrement
            )
        cropUtils.saveBitmapInFile(
            bitmap = rotatedBitmap,
            destinationFile = tempFile,
            onSuccess = {
                key = UUID.randomUUID()
            }
        )
    }

    fun clear() {
        tempFile.delete()
    }
}

@Composable
fun rememberLbCropViewState(
    originalBitmap: Bitmap,
    originalOrientation: Int,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    context: Context = LocalContext.current,
    finalImageMinSize: CropImageSize = CropImageSize.Zero
): LbCropViewState {
    val zoomState =
        rememberZoomState(
            maxScale = Float.MAX_VALUE
        )
    return remember {
        LbCropViewState(
            originalBitmap = originalBitmap,
            originalOrientation = originalOrientation,
            coroutineDispatcher = coroutineDispatcher,
            zoomState = zoomState,
            context = context,
            finalImageMinSize = finalImageMinSize
        )
    }
}
