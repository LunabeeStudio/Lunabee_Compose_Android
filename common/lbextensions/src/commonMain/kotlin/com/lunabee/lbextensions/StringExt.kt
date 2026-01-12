package com.lunabee.lbextensions

/**
 * Replace matching regex by an empty string
 * @param regex The regular expression to match
 * @return The string with matching regular expression removed
 */
fun String.remove(regex: Regex): String = replace(regex, "")

/**
 * Replace the matching string by an empty string
 * @param string The string to match
 * @return The string with matching string removed
 */
fun String.remove(string: String): String = replace(string, "")
