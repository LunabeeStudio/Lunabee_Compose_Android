package com.lunabee.lbextensions.view

import android.view.View
import com.google.android.material.appbar.AppBarLayout

/**
 * Set the correct elevation state (lift) according to the [scrollingView] scroll position.
 * Should lift test come from AppBarLayout.java::shouldLift (private fun)
 *
 * @param scrollingView
 */
fun AppBarLayout.refreshLift(scrollingView: View) {
    isLifted = (scrollingView.canScrollVertically(-1) || scrollingView.scrollY > 0)
}

/**
 * Disable lift on scroll behavior and remove the view registered as scroll target.
 *
 * @param lifted Sets whether the AppBarLayout should be in a lifted state or not.
 */
fun AppBarLayout.disableLiftOnScroll(lifted: Boolean = false) {
    post {
        isLiftOnScroll = false
        liftOnScrollTargetViewId = View.NO_ID
        isLifted = lifted
        setLiftableOverrideEnabled(true)
    }
}
