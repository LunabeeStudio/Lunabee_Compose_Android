@file:Suppress("unused")
@file:JvmName("ScrollingViewUtils")

package com.lunabee.lbextensions

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lunabee.lbextensions.view.refreshLift

/**
 * Hide [floatingActionButton] when the recyclerView is scrolling and show it again when the
 * recyclerView is stopped and the [bottomSheetBehavior] is in state HIDDEN.
 */
fun RecyclerView.hideFabOnScrollBehavior(
    floatingActionButton: FloatingActionButton?,
    bottomSheetBehavior: BottomSheetBehavior<View>? = null,
) {
    floatingActionButton?.let { fab ->
        this.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (bottomSheetBehavior == null || bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                            Handler(Looper.getMainLooper()).postDelayed(fab::show, 500)
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 || dy < 0 && fab.isShown) {
                        fab.hide()
                    }
                }
            },
        )
    }
}

/**
 * Close the visible keyboard when the recyclerView is dragging.
 */
fun RecyclerView.closeKeyboardOnScroll(context: Context?) {
    this.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputMethodManager?.hideSoftInputFromWindow(recyclerView.windowToken, 0)
                }
            }
        },
    )
}

/**
 * Set the liftable property of [appBarLayout] to [liftable], if [liftable] is set to true the
 * [appBarLayout] will have a elevation set to zero until the [RecyclerView] is scrolled.
 * Otherwise the appBarLayout will have the default elevation.
 */
@Deprecated(
    "Use registerToAppBarLayoutForLiftOnScroll instead",
    ReplaceWith("registerToAppBarLayoutForLiftOnScroll"),
)
fun RecyclerView.isAppBarLayoutLiftable(appBarLayout: AppBarLayout, liftable: Boolean = true) {
    appBarLayout.setLiftable(liftable)
    this.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                appBarLayout.refreshLift(recyclerView)
            }
        },
    )
}

/**
 * Set the liftable property of [appBarLayout] to [liftable], if [liftable] is set to true the [appBarLayout]will have a
 * elevation set to zero until the [NestedScrollView] is scrolled.
 * Otherwise the appBarLayout will have the default elevation.
 */
@Deprecated(
    "Use registerToAppBarLayoutForLiftOnScroll instead",
    ReplaceWith("registerToAppBarLayoutForLiftOnScroll"),
)
fun NestedScrollView.isAppBarLayoutLiftable(appBarLayout: AppBarLayout, liftable: Boolean = true) {
    appBarLayout.setLiftable(liftable)
    this.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
        v?.let {
            appBarLayout.refreshLift(it)
        }
    }
}

/**
 * Change the state of a [bottomSheetBehavior] to HIDDEN when the receiver is dragging.
 */
fun RecyclerView.closeBottomSheetOnScroll(bottomSheetBehavior: BottomSheetBehavior<View>) {
    this.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        },
    )
}

/**
 * Register the view that the [appBarLayout] should use to determine whether it should be lifted.
 *
 * @param appBarLayout The appBar to lift on scroll.
 */
fun View.registerToAppBarLayoutForLiftOnScroll(appBarLayout: AppBarLayout) {
    // Ensure lift code is called after view is on screen
    appBarLayout.post {
        appBarLayout.setLiftableOverrideEnabled(false)
        appBarLayout.isLiftOnScroll = true
        appBarLayout.liftOnScrollTargetViewId = id
    }
}
