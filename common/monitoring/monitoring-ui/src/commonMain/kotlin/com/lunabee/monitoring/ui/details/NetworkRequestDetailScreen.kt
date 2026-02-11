package studio.lunabee.monitoring.ui.details

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
import studio.lunabee.monitoring.ui.res.CoreDrawable
import studio.lunabee.monitoring.ui.res.CoreString
import studio.lunabee.monitoring.ui.theme.CorePayload
import studio.lunabee.monitoring.ui.theme.CoreRequest
import studio.lunabee.monitoring.ui.theme.CoreRequestConfig
import studio.lunabee.monitoring.ui.theme.CoreTopBar
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studio.lunabee.monitoring.ui.res.ic_bin
import studio.lunabee.monitoring.ui.res.networkRequestDetailReceivedTitle
import studio.lunabee.monitoring.ui.res.networkRequestDetailSentTitle
import studio.lunabee.monitoring.ui.res.networkRequestDetailTitle

@Composable
internal fun NetworkRequestDetailScreen(
    navScope: NetworkRequestDetailNavScope,
    viewmodel: NetworkRequestDetailViewModel = koinViewModel(),
) {
    val request: LBRequest? by viewmodel.request.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
        ),
    ) {
        stickyHeader {
            CoreTopBar(
                title = stringResource(CoreString.networkRequestDetailTitle),
                onBackClicked = navScope.navigateBack,
                actions = {
                    IconButton(
                        onClick = {
                            viewmodel.deleteRequest()
                            navScope.navigateBack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(CoreDrawable.ic_bin),
                            contentDescription = "Delete request",
                        )
                    }
                },
            )
        }

        request?.let { safeRequest ->
            item {
                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                CoreRequest(
                    request = safeRequest,
                    config = CoreRequestConfig.Details,
                    modifier = modifier,
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
            safeRequest.outgoingPayload?.let { outgoingPayload ->
                item {
                    CorePayload(
                        payload = outgoingPayload,
                        title = CoreString.networkRequestDetailSentTitle,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }
            safeRequest.incomingPayload?.let { outgoingPayload ->
                item {
                    CorePayload(
                        payload = outgoingPayload,
                        title = CoreString.networkRequestDetailReceivedTitle,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Serializable
internal data class NetworkRequestDetailDestination(
    val requestId: String,
) {
    companion object {
        fun composable(navGraphBuilder: NavGraphBuilder, navScope: NetworkRequestDetailNavScope) {
            navGraphBuilder.composable<NetworkRequestDetailDestination> {
                NetworkRequestDetailScreen(navScope = navScope)
            }
        }
    }
}

internal interface NetworkRequestDetailNavScope {
    val navigateBack: () -> Unit
}
