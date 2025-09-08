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
 * GlanceTextToImage.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 8/9/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.glance.helpers

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.text.TextAlign
import studio.lunabee.compose.glance.extensions.toPx
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Draw the text you provided as a [Bitmap].
 * @param text
 * @param color
 * @param fontSize
 * @param typeface
 * @param textAlign
 * @param letterSpacing
 * @param lineHeight
 * @param ellipsizedWidth width where the text should go to the line.
 * @param ellipsize
 * @param maxLines this parameters is only handled in API M+
 * @param breakStrategy this parameter is only handled in API M+, [Layout.BREAK_STRATEGY_SIMPLE] by default.
 * @param hyphenationFrequency this parameter is only handled in API M+, [Layout.HYPHENATION_FREQUENCY_NONE] by default.
 * @param justificationMode this parameter is only handled in API O+, [Layout.JUSTIFICATION_MODE_NONE] by default.
 */
@Composable
fun rememberTextImageBitmap(
    text: String,
    color: Color,
    fontSize: TextUnit,
    typeface: Typeface,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = 0.sp,
    ellipsizedWidth: Dp = LocalSize.current.width,
    ellipsize: TextUtils.TruncateAt? = null,
    maxLines: Int = Int.MAX_VALUE,
    breakStrategy: Int = 0,
    hyphenationFrequency: Int = 0,
    justificationMode: Int = 0,
    displayMetrics: DisplayMetrics = LocalContext.current.resources.displayMetrics
): Bitmap =
    remember(text) {
        textAsBitmap(
            text = text,
            color = color,
            typeface = typeface,
            textSize = fontSize.toPx(displayMetrics = displayMetrics),
            letterSpacing = letterSpacing.toPx(displayMetrics = displayMetrics),
            lineHeight = lineHeight.toPx(displayMetrics = displayMetrics),
            textAlign = textAlign,
            ellipsizedWidth = ellipsizedWidth.toPx(displayMetrics),
            ellipsize = ellipsize,
            maxLines = maxLines,
            breakStrategy = breakStrategy,
            hyphenationFrequency = hyphenationFrequency,
            justificationMode = justificationMode
        )
    }

/**
 * Map a [TextAlign] Glance to a [Layout.Alignment] to use it in a static layout.
 */
fun TextAlign.toLayoutAlignment(): Layout.Alignment =
    when (this) {
        TextAlign.Start,
        TextAlign.Left
        -> Layout.Alignment.ALIGN_NORMAL
        TextAlign.End,
        TextAlign.Right
        -> Layout.Alignment.ALIGN_OPPOSITE
        else -> Layout.Alignment.ALIGN_CENTER
    }

@Suppress("LongParameterList")
private fun textAsBitmap(
    text: String,
    typeface: Typeface,
    textSize: Float,
    letterSpacing: Float,
    lineHeight: Float,
    color: Color,
    ellipsizedWidth: Float,
    textAlign: TextAlign,
    ellipsize: TextUtils.TruncateAt?,
    maxLines: Int,
    breakStrategy: Int,
    hyphenationFrequency: Int,
    justificationMode: Int
): Bitmap {
    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = textSize
    paint.color = color.toArgb()
    paint.typeface = typeface
    paint.letterSpacing = letterSpacing

    val lineHeightToApply = if (lineHeight == 0f) 0f else abs(lineHeight - textSize)
    val staticLayout =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val builder =
                StaticLayout.Builder
                    .obtain(text, 0, text.length, paint, ellipsizedWidth.roundToInt())
                    .setAlignment(textAlign.toLayoutAlignment())
                    .setLineSpacing(lineHeightToApply, 1f)
                    .setIncludePad(false)
                    .setEllipsizedWidth(ellipsizedWidth.roundToInt())
                    .setEllipsize(ellipsize)
                    .setMaxLines(maxLines)
                    .setBreakStrategy(breakStrategy)
                    .setHyphenationFrequency(hyphenationFrequency)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setJustificationMode(justificationMode)
            }
            builder.build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(
                text,
                0,
                text.length,
                paint,
                ellipsizedWidth.roundToInt(),
                textAlign.toLayoutAlignment(),
                lineHeightToApply,
                1f,
                false,
                ellipsize,
                ellipsizedWidth.roundToInt()
            )
        }

    val image =
        Bitmap
            .createBitmap(staticLayout.width, staticLayout.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(image)
    staticLayout.draw(canvas)
    return image
}
