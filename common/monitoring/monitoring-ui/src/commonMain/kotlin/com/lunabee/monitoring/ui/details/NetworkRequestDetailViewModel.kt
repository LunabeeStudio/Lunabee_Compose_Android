package studio.lunabee.monitoring.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import studio.lunabee.monitoring.core.LBMonitoring
import studio.lunabee.monitoring.core.LBRequest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

internal class NetworkRequestDetailViewModel(
    private val monitoring: LBMonitoring,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val params: NetworkRequestDetailDestination = savedStateHandle.toRoute()

    val request: StateFlow<LBRequest?> = monitoring
        .getRequestByIdAsFlow(requestId = params.requestId.let(Uuid::parseHex))
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = null)

    fun deleteRequest() {
        viewModelScope.launch {
            monitoring.removeRequestById(requestId = params.requestId.let(Uuid::parseHex))
        }
    }
}
