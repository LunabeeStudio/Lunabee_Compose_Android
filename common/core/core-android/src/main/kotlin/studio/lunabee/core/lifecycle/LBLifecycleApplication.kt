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

package studio.lunabee.core.lifecycle

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
