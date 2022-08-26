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
 * LbcMenuIconButton.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/17/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction

/**
 * A standard [IconButton] for [androidx.compose.material.TopAppBar] menus or navigation icon
 * @param action icon, action and content description for your menu. See [LbcTopAppBarAction].
 */
@Composable
fun LbcMenuIconButton(
    action: LbcTopAppBarAction,
) {
    val modifier: Modifier = if (action.resizeIconWith != null) {
        Modifier
            .size(width = action.resizeIconWith.width, height = action.resizeIconWith.height)
    } else {
        Modifier
    }

    IconButton(
        onClick = action.action,
    ) {
        when (action) {
            is LbcTopAppBarAction.DrawableResAction -> {
                Icon(
                    painter = painterResource(id = action.iconRes),
                    modifier = modifier,
                    contentDescription = action.contentDescription,
                    tint = action.tintColor,
                )
            }
            is LbcTopAppBarAction.ImageVectorAction -> {
                Icon(
                    imageVector = action.vector,
                    modifier = modifier,
                    contentDescription = action.contentDescription,
                    tint = action.tintColor,
                )
            }
        }
    }
}
