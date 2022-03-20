package studio.lunabee.compose.extension

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.AppBarDefaults
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Elevation value for top bar depending on current [androidx.compose.foundation.lazy.LazyColumn] scroll status.
 */
val LazyListState.topAppBarElevation: Dp
    get() = if (firstVisibleItemIndex == 0) {
        minOf(firstVisibleItemScrollOffset.toFloat().dp, AppBarDefaults.TopAppBarElevation)
    } else {
        AppBarDefaults.TopAppBarElevation
    }
