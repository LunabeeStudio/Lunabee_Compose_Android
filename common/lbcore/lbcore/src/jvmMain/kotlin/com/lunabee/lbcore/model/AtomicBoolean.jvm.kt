package com.lunabee.lbcore.model

import java.util.concurrent.atomic.AtomicBoolean

actual class AtomicBoolean private constructor(private val atomicBoolean: AtomicBoolean) {
    actual constructor(initialValue: Boolean) : this(AtomicBoolean(initialValue))

    actual fun compareAndSet(expect: Boolean, update: Boolean): Boolean = atomicBoolean.compareAndSet(expect, update)

    actual val value: Boolean
        get() = atomicBoolean.get()

    actual fun getAndSet(newValue: Boolean): Boolean = atomicBoolean.getAndSet(newValue)
}
