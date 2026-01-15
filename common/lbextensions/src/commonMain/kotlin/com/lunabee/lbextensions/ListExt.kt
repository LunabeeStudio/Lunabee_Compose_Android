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
