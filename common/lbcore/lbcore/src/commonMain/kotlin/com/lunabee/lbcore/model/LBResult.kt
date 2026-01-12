package com.lunabee.lbcore.model

/**
 * Generic result wrapper to wrap data with a status.
 *
 * @param T Type of the wrapped data. Might be [Unit] if the expected result does not contain any data.
 */
sealed class LBResult<out T> {

    /**
     * Success implementation of [LBResult]
     *
     * @param T @inheritDoc
     * @property successData The final non-null data
     */
    data class Success<out T>(val successData: T) : LBResult<T>()

    /**
     * Success implementation of [LBResult]
     *
     * @param T @inheritDoc
     * @property throwable The throwable that caused the failure or null if non applicable
     * @property failureData The final data or null if non applicable
     */
    data class Failure<out T>(val throwable: Throwable? = null, val failureData: T? = null) : LBResult<T>() {
        /**
         * Secondary constructor to instantiate a failure result from an error string
         */
        @Suppress("unused")
        constructor(message: String) : this(throwable = Exception(message))
    }

    /**
     * Common getter for the data of any result states.
     */
    val data: T?
        get() {
            return when (this) {
                is Success -> successData
                is Failure -> failureData
            }
        }

    /**
     * @return the success data or thrown the failure throwable.
     */
    @Throws(Exception::class)
    fun getOrThrow(defaultMessage: String? = null): T = when (this) {
        is Failure -> throw this.throwable ?: Exception(defaultMessage ?: "Failed without exception")
        is Success -> this.successData
    }

    /**
     * Cast [LBResult] to [LBFlowResult]
     */
    fun asFlowResult(): LBFlowResult<T> {
        return when (this) {
            is Success -> LBFlowResult.Success(successData)
            is Failure -> LBFlowResult.Failure(throwable, failureData)
        }
    }
}
