/*
 * Copyright (c) 2025 Lunabee Studio
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
 * PresenterDemoList.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.presenternav

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import studio.lunabee.compose.common.MenuDescription
import studio.lunabee.compose.common.MenuEntry
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.demo.presenter.multistate.MultiStateDestination
import studio.lunabee.compose.demo.presenter.pullToRefresh.PullToRefreshDestination
import studio.lunabee.compose.demo.presenter.simple.SimpleExampleDestination
import studio.lunabee.compose.demo.presenter.timer.TimerDestination

@Composable
fun PresenterDemoList(
    navigate: (String) -> Unit,
) {
    val menus = listOf(
        MenuDescription(
            titleRes = LbcTextSpec.Raw("Check box"),
            subtitleRes = null,
            direction = { navigate(SimpleExampleDestination.route) },
        ),
        MenuDescription(
            titleRes = LbcTextSpec.Raw("Timer"),
            subtitleRes = null,
            direction = { navigate(TimerDestination.route) },
        ),
        MenuDescription(
            titleRes = LbcTextSpec.Raw("Pull to refresh"),
            subtitleRes = null,
            direction = { navigate(PullToRefreshDestination.route) },
        ),
        MenuDescription(
            titleRes = LbcTextSpec.Raw("Multi state"),
            subtitleRes = null,
            direction = { navigate(MultiStateDestination.route) },
        ),
    )

    LazyColumn {
        items(
            items = menus,
            key = { it.titleRes.hashCode() },
        ) { menu ->
            MenuEntry(menu = menu)
            HorizontalDivider()
        }
    }
}
