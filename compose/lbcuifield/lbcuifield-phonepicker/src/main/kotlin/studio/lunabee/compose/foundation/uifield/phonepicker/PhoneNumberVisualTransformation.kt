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

package studio.lunabee.compose.foundation.uifield.phonepicker

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale
import kotlin.compareTo
import kotlin.inc
import kotlin.text.isDigit

/*
 * Copyright (c) 2025 Lunabee Studio
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
 * PhoneNumberVisualTransformation.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/18/2025 - for the Lunabee Compose library.
 */

internal class PhoneNumberVisualTransformation(
    private val countryPhoneCode: String,
) : VisualTransformation {

    private val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
    private val prefix = "+$countryPhoneCode"

    override fun filter(text: AnnotatedString): TransformedText {
        val textFormatted = buildAnnotatedString {
            append(formatPhoneNumber(text.text, phoneNumberUtil))
        }
        val phoneOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var index = prefix.length
                var digitRemaining = offset
                while (digitRemaining > 0 && index < textFormatted.text.length) {
                    if (textFormatted.text[index].isDigit()) {
                        digitRemaining--
                    }
                    index++
                }
                return index
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= prefix.length) return 0
                val suffixBeforeSelection =
                    textFormatted.text.substring((prefix.length) until offset)
                return suffixBeforeSelection.filter { it.isDigit() }.length
            }
        }
        return TransformedText(textFormatted, phoneOffsetTranslator)
    }

    private fun formatPhoneNumber(
        rawPhoneNumber: String,
        phoneNumberUtil: PhoneNumberUtil,
    ): String {
        val fullPhoneNumber = prefix + rawPhoneNumber
        return try {
            val phoneNumber = phoneNumberUtil.parse(fullPhoneNumber, Locale.getDefault().country)
            return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (e: Exception) {
            fullPhoneNumber
        }
    }
}
