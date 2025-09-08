package studio.lunabee.compose.glance.ui

import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import studio.lunabee.compose.glance.R

/**
 * Put a XML background behind any Glance compose view.
 * DO NOT USE [GlanceBackground] if you have a simple image or color to use as background, use [GlanceModifier] instead.
 */
@Composable
fun GlanceBackground(
    @DrawableRes background: Int,
    modifier: GlanceModifier = GlanceModifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        val remoteViews =
            RemoteViews(LocalContext.current.packageName, R.layout.lbc_glance_widget_background)
        remoteViews.setInt(R.id.lbc_glance_widget_background, "setBackgroundResource", background)
        AndroidRemoteViews(
            remoteViews = remoteViews,
            containerViewId = R.id.lbc_glance_widget_background,
            content = content
        )
    }
}
