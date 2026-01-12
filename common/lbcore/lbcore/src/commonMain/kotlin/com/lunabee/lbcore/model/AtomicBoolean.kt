package com.lunabee.lbcore.model

expect class AtomicBoolean(initialValue: Boolean = false) {
    /**
     * Atomically sets the value to the given updated value if the current value == the expected value.
     */
    fun compareAndSet(expect: Boolean, update: Boolean): Boolean

    /**
     * The current value.
     */
    val value: Boolean

    /**
     * Atomically sets to the given value and returns the previous value.
     */
    fun getAndSet(newValue: Boolean): Boolean
}
