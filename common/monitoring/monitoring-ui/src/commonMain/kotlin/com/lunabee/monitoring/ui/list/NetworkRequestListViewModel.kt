package studio.lunabee.monitoring.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import studio.lunabee.monitoring.core.LBMonitoring
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class NetworkRequestListViewModel(
    private val monitoring: LBMonitoring,
) : ViewModel() {

    private val pagingRequests = monitoring
        .getPagingRequests()
        .cachedIn(viewModelScope)

    val uiState: StateFlow<NetworkRequestListUiState> = monitoring.getRequestCountAsFlow()
        .map { count ->
            NetworkRequestListUiState(
                pagingRequests = pagingRequests,
                requestCount = count,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NetworkRequestListUiState(pagingRequests, 0),
        )

    fun flushRequests() {
        viewModelScope.launch {
            monitoring.flush()
        }
    }
}
