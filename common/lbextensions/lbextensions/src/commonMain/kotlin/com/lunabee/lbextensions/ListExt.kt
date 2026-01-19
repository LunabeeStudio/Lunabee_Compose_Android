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

package com.lunabee.lbextensions

/**
 * Creates a string from all the elements that are not null separated using [separator]
 * and using the given [prefix] and [postfix] if supplied.
 *
 * If the collection could be huge, you can specify a non-negative value of [limit], in which case only the first [limit]
 * elements will be appended, followed by the [truncated] string (which defaults to "...").
 *
 */
fun <T : Any> Iterable<T?>.joinToStringNotNull(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "…",
    transform: ((T) -> CharSequence)? = null,
): String = this
    .filterNotNull()
    .filter {
        (it as? String)?.isNotEmpty() ?: true
    }.joinToString(
        separator,
        prefix,
        postfix,
        limit,
        truncated,
        transform,
    )

/**
 * Creates a string from all the elements that are not null separated using [separator]
 * and using the given [prefix] and [postfix] if supplied.
 *
 * If the collection could be huge, you can specify a non-negative value of [limit], in which case only the first [limit]
 * elements will be appended, followed by the [truncated] string (which defaults to "...").
 *
 * The operation is _terminal_.
 *
 */
fun <T : Any> Sequence<T?>.joinToStringNotNull(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "…",
    transform: ((T) -> CharSequence)? = null,
): String = this
    .filterNotNull()
    .filter {
        (it as? String)?.isNotEmpty() ?: true
    }.joinToString(
        separator,
        prefix,
        postfix,
        limit,
        truncated,
        transform,
    )
