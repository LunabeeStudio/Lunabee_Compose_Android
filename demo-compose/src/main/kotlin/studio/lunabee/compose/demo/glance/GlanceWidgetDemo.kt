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

package studio.lunabee.compose.demo.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.TextAlign
import studio.lunabee.compose.MainActivity
import studio.lunabee.compose.R
import studio.lunabee.compose.glance.extensions.cornerRadiusCompat
import studio.lunabee.compose.glance.ui.GlanceBackground
import studio.lunabee.compose.glance.ui.GlanceRoot
import studio.lunabee.compose.glance.ui.GlanceTypefaceButton
import studio.lunabee.compose.glance.ui.GlanceTypefaceText
import studio.lunabee.compose.glance.ui.GlanceViewFlipper
import kotlin.time.Duration.Companion.seconds

class GlanceWidgetDemoReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = GlanceWidgetDemo()
}

class GlanceWidgetDemo : GlanceAppWidget() {
    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { Render() }
    }

    @Composable
    fun Render() {
        GlanceRoot {
            Column(
                modifier = GlanceModifier
                    .cornerRadiusCompat(
                        cornerRadius = 16.dp,
                        color = Color(0xFFE5DDC8),
                        size = DpSize(LocalSize.current.width, LocalSize.current.height),
                    ),
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            ) {
                Spacer(modifier = GlanceModifier.defaultWeight())

                GlanceViewFlipper(
                    viewFlipperLayout = R.layout.glance_view_flipper,
                    viewFlipperViewId = R.id.glance_view_flipper,
                    flipInterval = 2.seconds,
                    modifier = GlanceModifier
                        .size(60.dp),
                ) {
                    listOf(
                        R.drawable.ic_view_flipper_1,
                        R.drawable.ic_view_flipper_2,
                        R.drawable.ic_view_flipper_3,
                        R.drawable.ic_view_flipper_4,
                    ).forEach { resId ->
                        Image(
                            provider = ImageProvider(resId = resId),
                            contentDescription = null,
                            modifier = GlanceModifier
                                .size(size = 48.dp),
                        )
                    }
                }

                // The text will automatically fill the width and go to multiline if needed.
                // You can specify a limited width with ellipsizedWidth (if you want to apply a padding for example).
                GlanceTypefaceText(
                    text = "Welcome in this beautiful widget! Click on the button bellow to launch the application from here 😱!",
                    color = Color.Black,
                    fontSize = 14.sp,
                    typeface = ResourcesCompat.getFont(LocalContext.current, R.font.robot_regular)!!,
                    modifier = GlanceModifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = GlanceModifier.height(8.dp))

                // Demonstration of a custom button with an XML background and a custom typeface. You can also use a classic
                // Glance button if you don't have specific font or an ImageProvider/ColorProvider with the Modifier.
                GlanceBackground(
                    background = R.drawable.widget_button_gradient_bg,
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    GlanceTypefaceButton(
                        text = "Open the app",
                        color = Color.White,
                        fontSize = 12.sp,
                        typeface = ResourcesCompat.getFont(LocalContext.current, R.font.write)!!,
                        action = actionStartActivity(MainActivity::class.java),
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .height(height = 48.dp),
                    )
                }
                Spacer(modifier = GlanceModifier.defaultWeight())
            }
        }
    }
}
