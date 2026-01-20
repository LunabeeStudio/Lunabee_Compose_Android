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

import android.graphics.Bitmap
import kotlin.math.abs

/**
 * Compute distance between 2 bitmaps
 * https://rosettacode.org/wiki/Percentage_difference_between_images#Kotlin (version 1.2.10)
 */
object BitmapUtils {
    fun getDifferencePercent(img1: Bitmap, img2: Bitmap): Double {
        val width = img1.width
        val height = img1.height
        val width2 = img2.width
        val height2 = img2.height
        if (width != width2 || height != height2) {
            val f = "(%d,%d) vs. (%d,%d)".format(width, height, width2, height2)
            throw IllegalArgumentException("Images must have the same dimensions: $f")
        }
        var diff = 0L
        repeat(height) { y ->
            repeat(width) { x ->
                diff += pixelDiff(img1.getPixel(x, y), img2.getPixel(x, y))
            }
        }
        val maxDiff = 3L * 255 * width * height
        return 100.0 * diff / maxDiff
    }

    private fun pixelDiff(rgb1: Int, rgb2: Int): Int {
        val r1 = (rgb1 shr 16) and 0xff
        val g1 = (rgb1 shr 8) and 0xff
        val b1 = rgb1 and 0xff
        val r2 = (rgb2 shr 16) and 0xff
        val g2 = (rgb2 shr 8) and 0xff
        val b2 = rgb2 and 0xff
        return abs(r1 - r2) + abs(g1 - g2) + abs(b1 - b2)
    }
}
