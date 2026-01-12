package com.lunabee.lbextensions

import java.text.Normalizer
import java.util.Locale

/**
 * Remove all the diacritics from a string.
 *
 * @return A diacritic free String.
 */
fun String.removeAccents(): String = Normalizer.normalize(
    this,
    Normalizer.Form.NFD,
).replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")

/**
 * Remove the diacritics from a string and lowercase the result.
 *
 * @return A lowercase diacritic free String.
 */
fun String.transformForSearch(): String = this.removeAccents().lowercase(Locale.getDefault())

/**
 * Capitalize the string according to the locale
 * @return The capitalized string
 */
fun String.titlecaseFirstChar(): String = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
