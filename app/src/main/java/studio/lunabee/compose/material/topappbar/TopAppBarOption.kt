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
 * TopAppBarOption.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/18/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.topappbar

import androidx.annotation.StringRes
import studio.lunabee.compose.R

enum class TopAppBarOption(
    @StringRes val switchRes: Int,
) {
    Elevation(
        switchRes = R.string.top_bar_screen_elevate_top_bar_action,
    ),
    StatusBarPadding(
        switchRes = R.string.top_bar_screen_elevate_status_bar_padding_action,
    ),
    Loading(
        switchRes = R.string.top_bar_screen_is_loading_action,
    ),
    ShowMenuWhenLoading(
        switchRes = R.string.top_bar_screen_is_menu_showing_action,
    ),
    Navigation(
        switchRes = R.string.top_bar_screen_navigation_action,
    ),
}
