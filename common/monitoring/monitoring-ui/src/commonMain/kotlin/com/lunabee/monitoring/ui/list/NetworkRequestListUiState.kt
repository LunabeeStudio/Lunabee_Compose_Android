package studio.lunabee.monitoring.ui.list

import androidx.paging.PagingData
import studio.lunabee.monitoring.core.LBRequest
import kotlinx.coroutines.flow.Flow

data class NetworkRequestListUiState(
    val pagingRequests: Flow<PagingData<LBRequest>>,
    val requestCount: Int,
)
