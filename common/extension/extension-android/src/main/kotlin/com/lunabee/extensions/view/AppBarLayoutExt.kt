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
