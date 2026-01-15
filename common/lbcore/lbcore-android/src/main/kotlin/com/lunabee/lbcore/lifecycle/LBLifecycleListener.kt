package com.lunabee.lbcore.lifecycle

/**
 * Used in LBApplication to provide a callback on app's lifecycle changes
 */
interface LBLifecycleListener {
    fun onAppEnterInBackground()

    fun onAppEnterInForeground()
}
