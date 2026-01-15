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

import java.text.Normalizer
import java.util.Locale

/**
 * Remove all the diacritics from a string.
 *
 * @return A diacritic free String.
 */
fun String.removeAccents(): String = Normalizer
    .normalize(
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
fun String.titlecaseFirstChar(): String = replaceFirstChar {
    if (it.isLowerCase()) {
        it.titlecase(Locale.getDefault())
    } else {
        it
            .toString()
    }
}
