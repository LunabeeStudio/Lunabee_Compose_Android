package studio.lunabee.compose.glance.helpers

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.glance.action.Action
import androidx.glance.action.action
import androidx.glance.appwidget.action.actionStartActivity

/**
 * [GlanceActions] object provides methods to get an [Action] that can be executed on any clickable element in a Glance context.
 * Methods provided here are just shortcuts to start system activities. If you want to start an activity of yours, simply use directly
 * [actionStartActivity], or for any simple [Action], use the compose method [action].
 */
@Suppress("unused")
object GlanceActions {
    /**
     * Get an [Action] to be used directly in your [androidx.glance.Button] or with a [androidx.glance.GlanceModifier] that
     * redirect the user to application settings (i.e if you want for example to let user enables a permission manually).
     * @param packageName
     */
    fun getSettingsIntentAction(packageName: String): Action = actionStartActivity(
        intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        },
    )
}
