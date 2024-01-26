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
 * StyledTextItem.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/29/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import studio.lunabee.compose.foundation.model.TextToHighlight

/**
 * Display a base text and highlight some part of it.
 *
 * @param rawBaseText full text to display.
 * @param rawBaseTextStyle [TextStyle] of part of the text that will not be highlighted.
 * @param textToHighLightList a list of [TextToHighlight] representing all elements to highlight.
 * All part of the text matching will be highlighted.
 * @param modifier a default [Modifier]
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as
 * if there was unlimited horizontal space. If softWrap is false, overflow and TextAlign may have unexpected effects.
 * @param overflow how visual overflow should be handled.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 * If the text exceeds the given number of lines, it will be truncated according to overflow and softWrap.
 * @param onTextLayout callback that is executed when a new text layout is calculated. A TextLayoutResult object that callback provides
 * contains paragraph information, size of the text, baselines and other details. The callback can be used to add additional decoration
 * or functionality to the text. For example, to draw selection around the text.
 */
@Composable
fun StyledTextItem(
    rawBaseText: String,
    rawBaseTextStyle: TextStyle,
    textToHighLightList: Set<TextToHighlight>,
    modifier: Modifier = Modifier,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = { },
) {
    val annotatedString = buildAnnotatedString {
        // Default style use for non highlighted text.
        pushStyle(style = rawBaseTextStyle.toSpanStyle())

        var index = 0
        while (index < rawBaseText.length) {
            // Check if we have a corresponding text to highlight
            val textToHighlight = textToHighLightList.firstOrNull { textToHighLight ->
                rawBaseText.indexOf(string = textToHighLight.text, startIndex = index, ignoreCase = textToHighLight.ignoreCase) == index
            }
            if (textToHighlight == null) {
                // Append raw text with default style until we reach startIndexOfTextColor
                append(text = rawBaseText[index].toString())
                index++
            } else {
                // Substring to keep initial case.
                val textToAppend = rawBaseText.substring(startIndex = index, endIndex = index + textToHighlight.text.length)

                // Set a tag to retrieve our annotation later if needed for a click for example.
                // Set a start and an end index to retrieve this annotation later when clicking on it.
                addStringAnnotation(
                    tag = textToHighlight.tag,
                    annotation = textToAppend,
                    start = index,
                    end = index + textToHighlight.text.length,
                )

                // Push style for new annotation
                pushStyle(style = textToHighlight.style)
                append(text = textToAppend)
                // Remove pushed style to go back to raw text style.
                pop()

                // Skip index of highlighted text.
                index += textToHighlight.text.length
            }
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString
                // Only get annotation in the range of click. Use same start and end to only get the elements clicked.
                .getStringAnnotations(start = offset, end = offset)
                // So at this point, we are sure to have only one.
                .getOrNull(index = 0)
                // Find TextToHighlight object corresponding to range tag.
                ?.let { range -> textToHighLightList.firstOrNull { textToHighlight -> textToHighlight.tag == range.tag } }
                // Execute action.
                ?.action
                ?.invoke()
        },
        modifier = modifier,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}
