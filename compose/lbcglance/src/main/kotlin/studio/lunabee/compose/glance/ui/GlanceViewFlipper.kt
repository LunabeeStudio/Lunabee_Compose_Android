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

package studio.lunabee.compose.glance.ui

import android.widget.RemoteViews
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.AndroidRemoteViews
import kotlin.time.Duration

/**
 * A [android.widget.ViewFlipper] XML view as Compose view to animate change between all view described in [content].
 * Don't forget to use autoStart = true in your [viewFlipperLayout].
 * @param viewFlipperLayout XML layout with the description of the [android.widget.ViewFlipper]. This is mandatory as all methods of this
 * object can not be used with [RemoteViews] (there is no method annotated with @RemotableViewMethod other than setFlipInterval).
 * @param viewFlipperViewId ID of the [android.widget.ViewFlipper] in [viewFlipperLayout]
 * @param flipInterval
 * @param modifier
 * @param content all view that will be animated
 */
@Composable
fun GlanceViewFlipper(
    @LayoutRes viewFlipperLayout: Int,
    @IdRes viewFlipperViewId: Int,
    flipInterval: Duration,
    modifier: GlanceModifier = GlanceModifier,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val remoteViews = RemoteViews(context.packageName, viewFlipperLayout)
    remoteViews.setInt(viewFlipperViewId, "setFlipInterval", flipInterval.inWholeMilliseconds.toInt())

    AndroidRemoteViews(
        modifier = modifier,
        remoteViews = remoteViews,
        containerViewId = viewFlipperViewId,
        content = content,
    )
}
