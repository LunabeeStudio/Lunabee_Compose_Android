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
 * PhoneCountryCodePickerDelegateImpl.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/17/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.phonepicker.delegate

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.hbb20.CCPCountry
import com.hbb20.CountryCodePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import studio.lunabee.compose.core.LbcImageSpec
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData
import studio.lunabee.compose.foundation.uifield.field.text.NormalUiTextField
import studio.lunabee.compose.foundation.uifield.phonepicker.CountryCodeFieldData
import studio.lunabee.compose.foundation.uifield.phonepicker.CountryCodeSearchItem
import studio.lunabee.compose.foundation.uifield.phonepicker.SelectedCountryPhoneCode
import studio.lunabee.compose.foundation.uifield.phonepicker.ext.normalized
import kotlinx.coroutines.launch
import java.util.Locale

internal class PhoneCountryCodeSearchDelegate(
    savedStateHandle: SavedStateHandle,
    searchFieldLabel: LbcTextSpec?,
    searchFieldPlaceHolder: LbcTextSpec?,
    searchFieldStyleData: UiFieldStyleData,
    private val coroutineScope: CoroutineScope,
) {

    private var allCountryCodes: List<CountryCodeSearchItem> = emptyList()

    private val _uiState: MutableStateFlow<PhoneCountryCodeSearchUiState> by lazy {
        MutableStateFlow(
            PhoneCountryCodeSearchUiState(
                countryCodesToDisplay = allCountryCodes,
                searchedText = "",
                selectedCountryPhoneCode = null,
            ),
        )
    }
    internal val uiState: StateFlow<PhoneCountryCodeSearchUiState> by lazy { _uiState.asStateFlow() }

    val searchUiField: NormalUiTextField by lazy {
        NormalUiTextField(
            id = SearchUiFieldId,
            initialValue = "",
            label = searchFieldLabel,
            placeholder = searchFieldPlaceHolder,
            isFieldInError = { null },
            savedStateHandle = savedStateHandle,
            uiFieldStyleData = searchFieldStyleData,
            onValueChange = ::onSearchedTextChange,
        )
    }

    private fun onSearchedTextChange(value: String) {
        updateUiStateWithSearch(value)
    }

    private fun updateUiStateWithSearch(searchedText: String) {
        _uiState.value = uiState.value.copy(
            countryCodesToDisplay = allCountryCodes.filter { country ->
                country.name.normalized()
                    .contains(searchedText.normalized())
            },
            searchedText = searchedText,
        )
    }

    internal fun initCountryCodesList(
        context: Context,
        initialPhoneNumber: CountryCodeFieldData,
    ) {
        coroutineScope.launch {
            allCountryCodes =
                CCPCountry.getLibraryMasterCountryList(context, CountryCodePicker.Language.forCountryNameCode(Locale.getDefault().language))
                    .map { country ->
                        CountryCodeSearchItem(
                            name = country.name,
                            countryCode = country.phoneCode,
                            flag = LbcImageSpec.ImageDrawable(country.flagID),
                            isSelected = false,
                        )
                    }

            allCountryCodes.firstOrNull { it.countryCode == initialPhoneNumber.countryCode }
                ?.let { initialCountry ->
                    _uiState.value = uiState.value.copy(
                        selectedCountryPhoneCode = SelectedCountryPhoneCode(
                            name = initialCountry.name,
                            countryCode = initialCountry.countryCode,
                            flagImage = initialCountry.flag,
                        ),
                    )
                }

            updateUiStateWithSearch(searchUiField.value)
        }
    }

    internal fun onCountrySelected(searchItem: CountryCodeSearchItem) {
        _uiState.value = _uiState.value.copy(
            selectedCountryPhoneCode = SelectedCountryPhoneCode(
                name = searchItem.name,
                flagImage = searchItem.flag,
                countryCode = searchItem.countryCode,
            ),
        )
    }
}

private const val SearchUiFieldId: String = "SearchUiField"
