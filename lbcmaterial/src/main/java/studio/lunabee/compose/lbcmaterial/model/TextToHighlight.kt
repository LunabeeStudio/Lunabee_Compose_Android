/*
 * Copyright © 2022 Lunabee Studio
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
 *
 * TextToHighlight.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/29/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcmaterial.model

import androidx.compose.ui.text.SpanStyle

/**
 * Describe a text element to highlight in a global text.
 * Two highlighted text are considered as equal if the text is the same to avoid duplication.
 *
 * @param tag identifier to find it in a [androidx.compose.ui.text.AnnotatedString].
 * @param text text content.
 * @param style [SpanStyle] for this part of the text only.
 * @param ignoreCase whether case should be check or not.
 * @param action if not null, action will be executed when this part of the text will be clicked.
 */
data class TextToHighlight(
    val tag: String,
    val text: String,
    val style: SpanStyle,
    val ignoreCase: Boolean = true,
    val action: (() -> Unit)? = null,
) {
    override fun equals(other: Any?): Boolean {
        return text.equals(other = (other as? TextToHighlight)?.text, ignoreCase = ignoreCase)
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }
}
