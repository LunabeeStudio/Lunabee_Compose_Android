package com.lunabee.lbcore.lifecycle

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * Use LBApplication to easily manage lifecycle.
 * Just subclass LBApplication in you app Application Class
 * and call registerLifecycle when needed
 */
open class LBLifecycleApplication : Application(), DefaultLifecycleObserver {

    private var listener: LBLifecycleListener? = null

    /**
     * Register the app lifecycle and trigger change on foreground if needed
     * @param listener: you also can pass a LBLifecycleListener to do additional stuff on lifecycle changes
     */
    fun registerLifecycle(listener: LBLifecycleListener? = null) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        this.listener = listener
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        listener?.onAppEnterInForeground()
    }

    override fun onStop(owner: LifecycleOwner) {
        listener?.onAppEnterInBackground()
    }
}
