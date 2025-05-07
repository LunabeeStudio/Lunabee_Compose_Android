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
 * PhoneFieldRendered.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/18/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.phonepicker

import androidx.compose.runtime.Composable
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.countrypicker.SelectedCountry

/**
 * Interface for rendering the content of a phone field.
 *
 * This interface is used to customize the appearance and behavior of the phone number input field.
 * It provides a composable function to render the field content.
 */
interface PhoneFieldRenderer {

    /**
     * Composable function to render the content of a phone field.
     *
     * @param textField The composable function for rendering the text input field.
     *                  It takes a lambda `onFocusChange` to notify focus changes.
     * @param selectedCountry The currently selected country, or null if none is selected.
     * @param openCountryPicker Lambda to open the country picker dialog.
     * @param errorMessage Optional error message to display below the field.
     */
    @Composable
    fun FieldContent(
        textField: @Composable (onFocusChange: (focused: Boolean) -> Unit) -> Unit,
        selectedCountry: SelectedCountry?,
        openCountryPicker: () -> Unit,
        errorMessage: LbcTextSpec?,
    )
}
