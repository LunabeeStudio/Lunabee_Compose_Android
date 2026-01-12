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
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.TextAlign

/**
 * Display a button with a custom font in a Glance widget.
 * Custom font are not supported in a widget (even with the "old" XML API), so we need to draw the text and display an image instead.
 * DO NOT USE [GlanceTypefaceButton] if you have a simple text without specific font to display, it will be a little over-kill!
 */
@Composable
fun GlanceTypefaceButton(
    text: String,
    color: Color,
    fontSize: TextUnit,
    typeface: Typeface,
    action: Action,
    modifier: GlanceModifier = GlanceModifier,
    textAlign: TextAlign = TextAlign.Center,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = 0.sp,
) {
    Box(
        modifier = modifier
            .clickable(onClick = action),
        contentAlignment = Alignment.Center,
    ) {
        GlanceTypefaceText(
            text = text,
            color = color,
            fontSize = fontSize,
            typeface = typeface,
            modifier = GlanceModifier
                .fillMaxWidth(),
            textAlign = textAlign,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight,
        )
    }
}
