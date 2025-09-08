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
 * GlanceText.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 8/9/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.glance.ui

import android.graphics.Typeface
import android.text.TextUtils
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.text.TextAlign
import studio.lunabee.compose.glance.helpers.rememberTextImageBitmap

/**
 * Display a text in a Glance widget with a custom font.
 * Custom font are not supported in a widget (even with the "old" XML API), so we need to draw the text and display an image instead.
 * DO NOT USE [GlanceTypefaceText] if you have a simple text without specific font to display, it will be a little over-kill!
 *
 * @see [rememberTextImageBitmap] for more details about parameters.
 */
@Composable
fun GlanceTypefaceText(
    text: String,
    color: Color,
    fontSize: TextUnit,
    typeface: Typeface,
    modifier: GlanceModifier = GlanceModifier,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = 0.sp,
    ellipsizedWidth: Dp = LocalSize.current.width,
    ellipsize: TextUtils.TruncateAt? = null,
    maxLines: Int = Int.MAX_VALUE,
    breakStrategy: Int = 0,
    hyphenationFrequency: Int = 0,
    justificationMode: Int = 0
) {
    val bitmap =
        rememberTextImageBitmap(
            text = text,
            color = color,
            fontSize = fontSize,
            typeface = typeface,
            textAlign = textAlign,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight,
            ellipsizedWidth = ellipsizedWidth,
            ellipsize = ellipsize,
            maxLines = maxLines,
            breakStrategy = breakStrategy,
            hyphenationFrequency = hyphenationFrequency,
            justificationMode = justificationMode
        )

    Image(
        modifier = modifier,
        provider = remember { ImageProvider(bitmap = bitmap) },
        contentDescription = text
    )
}
