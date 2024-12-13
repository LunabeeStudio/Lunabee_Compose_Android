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
 * PresenterDemoList.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/14/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter.presenternav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.demo.presenter.multistate.MultiStateDestination
import studio.lunabee.compose.demo.presenter.pullToRefresh.PullToRefreshDestination
import studio.lunabee.compose.demo.presenter.simple.SimpleExempleDestination
import studio.lunabee.compose.demo.presenter.timer.TimerDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresenterDemoList(
    navigate: (String) -> Unit,
) {
    LazyColumn {
        item {
            DestinationRow(
                "Check box",
            ) {
                navigate(SimpleExempleDestination.route)
            }
        }
        item {
            DestinationRow(
                "Timer",
            ) {
                navigate(TimerDestination.route)
            }
        }
        item {
            DestinationRow(
                "Pull to refresh",
            ) {
                navigate(PullToRefreshDestination.route)
            }
        }
        item {
            DestinationRow(
                "Multi state",
            ) {
                navigate(MultiStateDestination.route)
            }
        }
    }
}

@Composable
private fun DestinationRow(
    title: String,
    onClick: () -> Unit,
) {
    Text(
        title,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
    )
    HorizontalDivider()
}
