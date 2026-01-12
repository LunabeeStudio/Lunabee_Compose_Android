package com.lunabee.lbextensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold

/**
 * Returns a flow of lists containing the results of applying the given [transform] function
 * to each element in the original collection.
 */
inline fun <T, R> Flow<Iterable<T>>.mapValues(crossinline transform: (T) -> R): Flow<List<R>> =
    map { list ->
        list.map { from ->
            transform(from)
        }
    }

/**
 * Transform the flow by emitting a Pair of [T] representing the previous and the new flow value. First emit contains the initial value
 * both in previous and next.
 */
fun <T> Flow<T>.withPreviousState(): Flow<Pair<T?, T>> = runningFold<T, Pair<T?, T>?>(null) { acc, value ->
    acc?.let { (_, new) ->
        new to value
    } ?: (value to value)
}.filterNotNull()
