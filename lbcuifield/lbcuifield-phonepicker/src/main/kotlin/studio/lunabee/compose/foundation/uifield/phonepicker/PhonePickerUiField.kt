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
 * PhoneUiField.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/17/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.phonepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiField
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.countrypicker.CountryPickerBottomSheetRenderer
import studio.lunabee.compose.foundation.uifield.countrypicker.CountrySearchDelegate
import studio.lunabee.compose.foundation.uifield.countrypicker.CountrySearchUiState
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleDataImpl

@Suppress("LongParameterList")
class PhonePickerUiField(
    override val initialValue: CountryCodeFieldData,
    override var label: LbcTextSpec?,
    override var placeholder: LbcTextSpec?,
    override val id: String,
    override val savedStateHandle: SavedStateHandle,
    override val isFieldInError: (CountryCodeFieldData) -> UiFieldError?,
    override val uiFieldStyleData: UiFieldStyleData = UiFieldStyleDataImpl(),
    override val onValueChange: (CountryCodeFieldData) -> Unit,
    override val readOnly: Boolean = false,
    override val enabled: Boolean = true,
    private val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    private val keyboardActions: KeyboardActions = KeyboardActions.Default,
    private val phoneFieldRenderer: PhoneFieldRenderer,
    private val coroutineScope: CoroutineScope,
    private val countryPickerBottomSheetRenderer: CountryPickerBottomSheetRenderer,
) : UiField<CountryCodeFieldData>() {

    private val json = Json.Default

    override val options: List<UiFieldOption> = emptyList()

    override fun valueToDisplayedString(value: CountryCodeFieldData): String = value.phoneNumber

    override fun valueToSavedString(value: CountryCodeFieldData): String = json.encodeToString(value)

    override fun savedValueToData(value: String): CountryCodeFieldData = json.decodeFromString(value)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Composable(modifier: Modifier) {
        val context = LocalContext.current
        val delegate = remember {
            CountrySearchDelegate(
                savedStateHandle = savedStateHandle,
                searchFieldLabel = countryPickerBottomSheetRenderer.searchedFieldLabel,
                searchFieldPlaceHolder = countryPickerBottomSheetRenderer.searchFieldPlaceHolder,
                searchFieldStyleData = countryPickerBottomSheetRenderer.searchFieldStyleData,
                coroutineScope = coroutineScope,
                context = context,
            )
        }
        val uiState: CountrySearchUiState by delegate.uiState.collectAsStateWithLifecycle()

        val collectedValue by mValue.collectAsState()

        val collectedError by error.collectAsState()
        var isPickerBottomSheetVisible by rememberSaveable { mutableStateOf(false) }

        phoneFieldRenderer.FieldContent(
            textField = { onFocusChange ->
                uiFieldStyleData.ComposeTextField(
                    value = valueToDisplayedString(collectedValue),
                    onValueChange = {
                        value = value.copy(phoneNumber = it)
                        dismissError()
                    },
                    modifier = modifier
                        .onFocusEvent {
                            if (!it.hasFocus && hasBeenFocused) {
                                checkAndDisplayError()
                            } else {
                                hasBeenFocused = true
                                dismissError()
                            }
                            onFocusChange(it.hasFocus)
                        },
                    placeholder = placeholder,
                    label = label,
                    trailingIcon = if (options.isNotEmpty()) {
                        { options.forEach { it.Composable(modifier = Modifier) } }
                    } else {
                        null
                    },
                    visualTransformation = PhoneNumberVisualTransformation(
                        countryPhoneCode = value.countryCode,
                    ),
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    maxLine = 1,
                    readOnly = readOnly,
                    enabled = enabled,
                    error = null, // We let the client handle the error message
                    interactionSource = null,
                )
            },
            openCountryPicker = { isPickerBottomSheetVisible = true },
            selectedCountry = uiState.selectedCountry,
            errorMessage = collectedError?.text,
        )

        if (isPickerBottomSheetVisible) {
            countryPickerBottomSheetRenderer.BottomSheetHolder(
                dismiss = {
                    isPickerBottomSheetVisible = false
                },
                searchField = {
                    delegate.searchUiField.Composable(
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                countriesList = { contentPadding, lazyListState ->
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = contentPadding,
                        state = lazyListState,
                    ) {
                        // Top anchor
                        item {
                            Box(modifier = Modifier.size(1.dp))
                        }
                        items(uiState.countryCodesToDisplay) { country ->
                            countryPickerBottomSheetRenderer.CountryRow(
                                countrySearchItem = country,
                                searchedText = uiState.searchedText,
                                onClick = {
                                    value = value.copy(countryCode = country.countryPhoneCode)
                                    delegate.onCountrySelected(country)
                                    isPickerBottomSheetVisible = false
                                },
                            )
                        }
                    }

                    LaunchedEffect(uiState.countryCodesToDisplay) {
                        lazyListState.scrollToItem(0)
                    }
                },
            )
        }

        LaunchedEffect(collectedValue.countryCode) {
            delegate.updateOnCountryCodeChange(collectedValue.countryCode)
        }

        LaunchedEffect(Unit) {
            delegate.initWithInitialCountryPhoneCode(collectedValue.countryCode)
        }
    }

    companion object {
        fun initialValueFromRawPhoneNumber(
            rawPhoneNumber: String,
        ): CountryCodeFieldData {
            try {
                val phoneNumber = PhoneNumberUtil.getInstance().parse(rawPhoneNumber, null)
                return CountryCodeFieldData(
                    phoneNumber = phoneNumber.nationalNumber.toString(),
                    countryCode = phoneNumber.countryCode.toString(),
                )
            } catch (e: Exception) {
                return CountryCodeFieldData(
                    phoneNumber = rawPhoneNumber,
                    countryCode = "",
                )
            }
        }
    }
}
