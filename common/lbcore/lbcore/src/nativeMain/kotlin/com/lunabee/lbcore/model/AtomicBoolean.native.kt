package com.lunabee.lbcore.model

import kotlin.concurrent.AtomicReference

actual class AtomicBoolean private constructor(private val atomicBoolean: AtomicReference<Boolean>) {
    actual constructor(initialValue: Boolean) : this(AtomicReference(initialValue))

    actual fun compareAndSet(expect: Boolean, update: Boolean): Boolean = atomicBoolean.compareAndSet(expect, update)
    actual val value: Boolean
        get() = atomicBoolean.value

    actual fun getAndSet(newValue: Boolean): Boolean = atomicBoolean.getAndSet(newValue)
}
