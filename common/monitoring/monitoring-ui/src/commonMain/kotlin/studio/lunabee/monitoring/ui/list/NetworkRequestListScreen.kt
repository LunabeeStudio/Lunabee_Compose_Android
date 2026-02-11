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

package studio.lunabee.monitoring.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import studio.lunabee.monitoring.core.LBRequest
import studio.lunabee.monitoring.ui.LazyPagingItems
import studio.lunabee.monitoring.ui.collectAsLazyPagingItems
import studio.lunabee.monitoring.ui.res.CoreDrawable
import studio.lunabee.monitoring.ui.res.CoreString
import studio.lunabee.monitoring.ui.theme.CoreRequest
import studio.lunabee.monitoring.ui.theme.CoreRequestConfig
import studio.lunabee.monitoring.ui.theme.CoreTopBar
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studio.lunabee.monitoring.ui.res.ic_bin
import studio.lunabee.monitoring.ui.res.networkRequestListTitle
import kotlin.uuid.Uuid

@Composable
internal fun NetworkRequestListScreen(
    navScope: NetworkRequestListNavScope,
    viewmodel: NetworkRequestListViewModel = koinViewModel(),
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val requests: LazyPagingItems<LBRequest> = uiState.pagingRequests.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        CoreTopBar(
            title = stringResource(
                CoreString.networkRequestListTitle,
                uiState.requestCount,
            ),
            onBackClicked = navScope.navigateBack,
            actions = {
                IconButton(
                    onClick = viewmodel::flushRequests,
                ) {
                    Icon(
                        painter = painterResource(CoreDrawable.ic_bin),
                        contentDescription = "Delete all requests",
                    )
                }
            },
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
            ),
        ) {
            items(
                count = requests.itemCount,
            ) { index ->
                requests[index]?.let { request ->
                    CoreRequest(
                        request = request,
                        config = CoreRequestConfig.List,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navScope.navigateToRequestDetail(request.id) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Serializable
internal data object NetworkRequestListDestination {
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: NetworkRequestListNavScope) {
        navGraphBuilder.composable<NetworkRequestListDestination> {
            NetworkRequestListScreen(navScope = navScope)
        }
    }
}

internal interface NetworkRequestListNavScope {
    val navigateBack: () -> Unit
    val navigateToRequestDetail: (requestId: Uuid) -> Unit
}
