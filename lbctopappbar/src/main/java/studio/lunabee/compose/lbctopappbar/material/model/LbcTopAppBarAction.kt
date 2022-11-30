/*
 * Copyright © 2022 Lunabee Studio
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
 * NavigationAction.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/16/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class that describes a menu action.
 * @param contentDescription optional content description. Must be define to ensure that user wants to handle accessibility or not.
 * @param action action to execute when associated [androidx.compose.runtime.Composable] will be clicked.
 * @param tintColor tint of your icon
 * @param resizeIconWith new size for your icon, will keep aspect of your icon if null
 */
sealed class LbcTopAppBarAction(
    val contentDescription: String?,
    val action: () -> Unit,
    val tintColor: Color,
    val resizeIconWith: LbcIconSize?,
) {
    /**
     * Data class that describes a menu action.
     * @param iconRes drawable res of your icon
     * @param contentDescription see parent [LbcTopAppBarAction]
     * @param action see parent [LbcTopAppBarAction]
     * @param tintColor see parent [LbcTopAppBarAction]
     * @param resizeIconWith see parent [LbcTopAppBarAction]
     */
    class DrawableResAction(
        @DrawableRes val iconRes: Int,
        contentDescription: String?,
        action: () -> Unit,
        tintColor: Color = Color.Unspecified,
        resizeIconWith: LbcIconSize? = null,
    ) : LbcTopAppBarAction(
        contentDescription = contentDescription,
        action = action,
        tintColor = tintColor,
        resizeIconWith = resizeIconWith,
    )

    /**
     * Data class that describes a menu action.
     * @param vector image vector of your icon
     * @param contentDescription see parent [LbcTopAppBarAction]
     * @param action see parent [LbcTopAppBarAction]
     * @param tintColor see parent [LbcTopAppBarAction]
     * @param resizeIconWith see parent [LbcTopAppBarAction]
     */
    class ImageVectorAction(
        val vector: ImageVector,
        contentDescription: String?,
        action: () -> Unit,
        tintColor: Color = Color.Unspecified,
        resizeIconWith: LbcIconSize? = null,
    ) : LbcTopAppBarAction(
        contentDescription = contentDescription,
        action = action,
        tintColor = tintColor,
        resizeIconWith = resizeIconWith,
    )
}
