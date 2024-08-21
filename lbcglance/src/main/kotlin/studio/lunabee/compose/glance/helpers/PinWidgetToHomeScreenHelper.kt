package studio.lunabee.compose.glance.helpers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Provides methods to pin the widget to the user home screen within the application.
 * As [AppWidgetManager.requestPinAppWidget] might not be available on all devices (even if the minimum API allows the feature),
 * [isPinSupported] should be called before trying to pin the widget, or to hide the option in the app for example.
 * This feature is only available on API O+ and there is no equivalent on older API unfortunately.
 * @see <a href="https://developer.android.com/develop/ui/views/appwidgets/discoverability#pin">Documentation</a>
 */
class PinWidgetToHomeScreenHelper(
    private val context: Context,
    private val dispatcher: CoroutineContext,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun <T : GlanceAppWidget, R : GlanceAppWidgetReceiver> pin(
        widgetClass: Class<T>,
        receiverClass: Class<R>,
        prepareIntent: Intent.(existingAppWidgetIds: IntArray) -> Unit = { },
    ) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val provider = ComponentName(context, receiverClass)
        CoroutineScope(dispatcher).launch {
            val glanceAppWidgetManager = GlanceAppWidgetManager(context)

            // Get existing app widget id for the widget before adding the new one.
            val existingAppWidgetIds: IntArray = glanceAppWidgetManager
                .getGlanceIds(widgetClass)
                .map { glanceId -> glanceAppWidgetManager.getAppWidgetId(glanceId = glanceId) }
                .toIntArray()

            // This intent will be trigger when the widget is added (i.e the user has picked it in the system bottom sheet).
            val intent = Intent(context, receiverClass).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                prepareIntent(existingAppWidgetIds)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
            appWidgetManager.requestPinAppWidget(provider, null, pendingIntent)
        }
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    fun isPinSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && AppWidgetManager.getInstance(context).isRequestPinAppWidgetSupported
    }
}
