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
 * GlanceModifierExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 8/9/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.glance.extensions

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.size
import kotlin.math.roundToInt

/**
 * According to the documentation of [androidx.glance.appwidget.CornerRadiusModifier],
 * rounded corners are applied with [GlanceModifier.cornerRadius] method only on API S+ (31+).
 * For below APIs, we need to use a [ShapeDrawable] to keep our design consistency across APIs.
 * This methods also applied [appWidgetBackground], so don't use it again (otherwise, it will crash)!
 * @param cornerRadius dimension in [Dp] of the desired corner radius. To keep your design consistency across all devices, it is mandatory,
 * as the default value used by Android on API31+ depends on the device. If you want to keep this behavior, you can use
 * [android.R.dimen.system_app_widget_background_radius] dimension.
 * @param color background color of your widget.
 * @param size size of your widget. Can be get with [LocalSize].
 */
fun GlanceModifier.appWidgetBackgroundCompat(
    cornerRadius: Dp,
    color: Color,
    size: DpSize,
): GlanceModifier {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        size(width = size.width, height = size.height)
            .background(color = color)
            .cornerRadius(radius = cornerRadius)
    } else {
        val radii = FloatArray(size = 8) { cornerRadius.value }
        val bitmap = ShapeDrawable(RoundRectShape(radii, null, null)).apply {
            paint.color = ColorUtils.setAlphaComponent(color.toArgb(), 255)
        }.toBitmap(width = size.width.value.roundToInt(), height = size.height.value.roundToInt())
        size(width = size.width, height = size.height)
            .background(imageProvider = ImageProvider(bitmap = bitmap))
    }
        .appWidgetBackground() // must be called only on the top view, will throw an exception if applied to multiple views.
}
