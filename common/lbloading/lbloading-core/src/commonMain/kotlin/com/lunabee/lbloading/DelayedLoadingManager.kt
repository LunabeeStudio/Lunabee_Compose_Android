package com.lunabee.lbloading

import com.lunabee.lbcore.helper.LBLoadingVisibilityDelayDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * [LoadingManager] which use [LBLoadingVisibilityDelayDelegate] to avoid loading blinks
 *
 * @property loadingDelegate The delay handling delegate
 */
class DelayedLoadingManager(
    private val loadingDelegate: LBLoadingVisibilityDelayDelegate,
) : LoadingManager() {
    private val _loadingState: MutableStateFlow<GlobalLoadingState> = MutableStateFlow(GlobalLoadingState.None)
    override val loadingState: StateFlow<GlobalLoadingState> = _loadingState

    override fun startLoading() {
        _loadingState.value = GlobalLoadingState.Blocking
        loadingDelegate.delayShowLoading {
            _loadingState.value = GlobalLoadingState.Loading
        }
    }

    override fun stopLoading() {
        loadingDelegate.delayHideLoading {
            _loadingState.value = GlobalLoadingState.None
        }
    }

    override fun startBlocking() {
        if (!loadingState.value.isBlocking && !loadingDelegate.isActive()) {
            _loadingState.value = GlobalLoadingState.Blocking
        }
    }

    override fun stopBlocking() {
        // Keep loading state if set
        if (!loadingState.value.isLoading && !loadingDelegate.isActive()) {
            _loadingState.value = GlobalLoadingState.None
        }
    }
}
