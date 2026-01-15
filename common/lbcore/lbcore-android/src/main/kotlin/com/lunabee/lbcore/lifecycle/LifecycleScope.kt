package com.lunabee.lbcore.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class LifecycleScope : DefaultLifecycleObserver, CoroutineScope {
    val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onDestroy(owner: LifecycleOwner) {
        job.cancel()
    }
}
