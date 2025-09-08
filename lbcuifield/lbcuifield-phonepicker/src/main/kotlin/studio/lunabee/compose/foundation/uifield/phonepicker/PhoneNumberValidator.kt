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
 * PhoneNumberValidator.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/25/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.phonepicker

import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

object PhoneNumberValidator {
    private val phoneNumberUtil = PhoneNumberUtil.getInstance()

    fun isValid(phoneNumber: String): Boolean =
        try {
            phoneNumberUtil
                .isValidNumber(phoneNumberUtil.parse(phoneNumber, Locale.getDefault().country))
        } catch (e: Exception) {
            false
        }
}
