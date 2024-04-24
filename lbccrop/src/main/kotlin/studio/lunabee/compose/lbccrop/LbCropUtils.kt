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
 * LbCropUtils.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/24/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbccrop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.abs
import kotlin.math.min

class LbCropUtils(
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    internal suspend fun saveBitmapInFile(
        bitmap: Bitmap,
        destinationFile: File,
        onSuccess: () -> Unit,
    ) {
        withContext(coroutineDispatcher) {
            destinationFile.outputStream().use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
            onSuccess()
        }
    }

    internal suspend fun cropImage(
        query: CropImageQuery,
    ): Bitmap {
        return withContext(coroutineDispatcher) {
            val originalWidth: Int = query.originalBitmap.width
            val originalHeight: Int = query.originalBitmap.height
            val isImageVertical = originalHeight > originalWidth

            // Transposed the bitmap so that its width is the same as the query.
            val scale = if (isImageVertical) {
                query.width / query.originalBitmap.width
            } else {
                query.height / query.originalBitmap.height
            }

            val matrix = Matrix().apply {
                setScale(scale, scale)
            }

            // Create a bitmap that has the same dimension of the crop zone (NO Zoom involved yet !)
            val scaledBitmap: Bitmap = Bitmap.createBitmap(
                query.originalBitmap,
                0,
                0,
                query.originalBitmap.width,
                query.originalBitmap.height,
                matrix,
                false,
            )

            val scaledImageWidth: Int = scaledBitmap.width
            val scaledImageHeight: Int = scaledBitmap.height

            // Image has an original zoom, to fits in the crop zone, we need the difference between the original and the one from the query.
            val zoomToApply: Float = query.scale / query.originalScale

            // Apply the zoom level to the crop zone so that it fits inside the scaled bitmap (as it's displayed)
            val transposedCropZoneWidth: Float = query.width / zoomToApply
            val transposedCropZoneHeight: Float = query.height / zoomToApply

            // Compute x & y translation with transposed crop zone
            // ℹ️ The OffsetX et OffsetY are the distance from the center of the image. To create a bitmap, the starting point is at the
            // Top left, so we need to transposed the offset into the new referential.
            val translationY = ((scaledImageHeight - transposedCropZoneHeight) / 2 - (query.offsetY / zoomToApply)).toInt()
            val translationX = ((scaledImageWidth - transposedCropZoneWidth) / 2 - (query.offsetX / zoomToApply)).toInt()

            // Safe guard in case of miscalculation or too large rounding.
            val maxY = abs((scaledImageHeight - transposedCropZoneHeight).toInt())
            val maxX: Int = abs((scaledImageWidth - transposedCropZoneWidth).toInt())

            try {
                Bitmap.createBitmap(
                    scaledBitmap,
                    min(abs(translationX), maxX),
                    min(abs(translationY), maxY),
                    transposedCropZoneWidth.toInt(),
                    transposedCropZoneHeight.toInt(),
                    null,
                    false,
                )
            } catch (e: Exception) {
                Log.e("CropImageException", e.toString())
                query.originalBitmap
            }
        }
    }

    internal suspend fun rotateImage(
        fileUri: Uri,
        rotationAngle: Float,
    ): Bitmap {
        return withContext(coroutineDispatcher) {
            val originalBitmap = BitmapFactory.decodeFile(
                fileUri.path.orEmpty(),
            )

            rotateImage(originalBitmap, rotationAngle)
        }
    }

    internal suspend fun rotateImage(
        bitmap: Bitmap,
        rotationAngle: Float,
    ): Bitmap {
        return withContext(coroutineDispatcher) {
            Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                Matrix().apply { setRotate(rotationAngle) },
                false,
            )
        }
    }
}
