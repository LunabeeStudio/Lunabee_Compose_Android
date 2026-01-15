package com.lunabee.lbloading

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SimpleLoadingManager : LoadingManager() {
    private val _loadingState: MutableStateFlow<GlobalLoadingState> = MutableStateFlow(GlobalLoadingState.None)
    override val loadingState: StateFlow<GlobalLoadingState> = _loadingState

    override fun startLoading() {
        _loadingState.value = GlobalLoadingState.Loading
    }

    override fun stopLoading() {
        _loadingState.value = GlobalLoadingState.None
    }

    override fun startBlocking() {
        if (!loadingState.value.isBlocking) {
            _loadingState.value = GlobalLoadingState.Blocking
        }
    }

    override fun stopBlocking() {
        // Keep loading state if set
        if (!loadingState.value.isLoading) {
            _loadingState.value = GlobalLoadingState.None
        }
    }
}
