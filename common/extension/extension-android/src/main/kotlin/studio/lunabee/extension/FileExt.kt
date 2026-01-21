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

package studio.lunabee.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.exifinterface.media.ExifInterface
import java.io.File
import kotlin.math.floor
import kotlin.math.min

/**
 * Write to [target] a new resized square jpeg. Crop if needed to keep ratio.
 * @param target The destination file
 * @param dstSize The size of square's side.
 * @return true if succeed or false if source file cannot be decoded or [target] can not be created.
 */
@Deprecated("Use downscaleAndCrop instead", ReplaceWith("downscaleAndCrop"))
fun File.resizeImageSquare(target: File, dstSize: Int): Boolean = downscaleAndCropPreR(target, dstSize, dstSize)

/**
 * Write to [target] a new resized jpeg. Crop if needed to keep ratio. If [dstWidth] and [dstHeight] (and the ratio) constraints are already
 * met, this does nothing and just copy receiver file to [target].
 * @param target The destination file
 * @param dstWidth The target width
 * @param dstHeight The target height (default to [dstWidth])
 * @param compressFormat Passthrough for compressFormat param of [Bitmap.compress]. Default to [Bitmap.CompressFormat.WEBP_LOSSLESS].
 * @param quality Passthrough for quality param of [Bitmap.compress]. Default to 100 (ignored for lossless format).
 * @return true if succeed or false if source file cannot be decoded or [target] can not be created.
 */
@RequiresApi(Build.VERSION_CODES.R)
fun File.downscaleAndCrop(
    target: File,
    dstWidth: Int,
    dstHeight: Int = dstWidth,
    compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.WEBP_LOSSLESS,
    quality: Int = 100,
): Boolean {
    return downscaleAndCropPreR(target, dstWidth, dstHeight, compressFormat, quality)
}

/**
 * See [downscaleAndCrop]
 *
 * @param compressFormat Default to [Bitmap.CompressFormat.WEBP].
 */
fun File.downscaleAndCropPreR(
    target: File,
    dstWidth: Int,
    dstHeight: Int = dstWidth,
    @Suppress("DEPRECATION") compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.WEBP,
    quality: Int = 100,
): Boolean {
    if (!target.exists()) {
        try {
            target.createNewFile()
        } catch (e: Exception) {
            return false
        }
    }

    val opts = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(absolutePath, opts)

    return if (opts.outWidth <= dstWidth &&
        opts.outHeight <= dstHeight &&
        opts.outWidth.toFloat() / opts.outHeight == dstWidth.toFloat() / dstHeight
    ) {
        copyTo(target, overwrite = true)
        true
    } else {
        val bitmap: Bitmap? = BitmapFactory.decodeFile(
            absolutePath,
            BitmapFactory.Options().apply {
                inSampleSize = min(opts.outWidth / dstWidth, opts.outHeight / dstHeight)
            },
        )

        bitmap?.let {
            val factor = min(bitmap.width / dstWidth.toFloat(), bitmap.height / dstHeight.toFloat())
            val croppedDstWidth = floor(dstWidth * factor).toInt()
            val croppedDstHeight = floor(dstHeight * factor).toInt()

            val orientation = ExifInterface(absolutePath).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL,
            )

            val mtx = Matrix().apply {
                preScale(
                    (dstWidth.toFloat() / croppedDstWidth).coerceAtMost(1f),
                    (dstHeight.toFloat() / croppedDstHeight).coerceAtMost(1f),
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_NORMAL -> postRotate(0f)
                    ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270f)
                }
            }

            val resizedBitmap = Bitmap.createBitmap(
                bitmap,
                (bitmap.width - croppedDstWidth) / 2,
                (bitmap.height - croppedDstHeight) / 2,
                croppedDstWidth,
                croppedDstHeight,
                mtx,
                true,
            )

            target.outputStream().use { output ->
                resizedBitmap.compress(compressFormat, quality, output)
            }

            true
        } ?: false
    }
}
