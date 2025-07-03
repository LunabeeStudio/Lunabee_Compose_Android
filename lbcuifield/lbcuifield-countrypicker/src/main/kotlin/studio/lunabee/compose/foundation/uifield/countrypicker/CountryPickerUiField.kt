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
 * CountryPickerUiField.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/7/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.countrypicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiField
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.field.UiFieldError
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleData
import studio.lunabee.compose.foundation.uifield.field.style.UiFieldStyleDataImpl
import java.util.Locale

@Suppress("LongParameterList")
class CountryPickerUiField(
    override val initialValue: CountryFieldData,
    override var label: LbcTextSpec?,
    override var placeholder: LbcTextSpec?,
    override val id: String,
    override val savedStateHandle: SavedStateHandle,
    override val isFieldInError: (CountryFieldData?) -> UiFieldError?,
    override val uiFieldStyleData: UiFieldStyleData = UiFieldStyleDataImpl(),
    override val onValueChange: (CountryFieldData) -> Unit,
    override val readOnly: Boolean = false,
    override val enabled: Boolean = true,
    private val coroutineScope: CoroutineScope,
    private val countryPickerBottomSheetRenderer: CountryPickerBottomSheetRenderer,
    private val trailingIcon: @Composable (() -> Unit)? = null,
) : UiField<CountryFieldData>() {

    private val json = Json.Default

    override val options: List<UiFieldOption> = emptyList()

    override fun valueToDisplayedString(value: CountryFieldData): String {
        return value.countryName
    }

    override fun valueToSavedString(value: CountryFieldData): String {
        return json.encodeToString(value)
    }

    override fun savedValueToData(value: String): CountryFieldData {
        return json.decodeFromString(value)
    }

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

        val focusManager = LocalFocusManager.current

        uiFieldStyleData.ComposeTextField(
            value = valueToDisplayedString(collectedValue),
            onValueChange = {
                dismissError()
            },
            modifier = modifier
                .onFocusEvent {
                    if (it.hasFocus) {
                        isPickerBottomSheetVisible = true
                    }
                    if (!it.hasFocus && hasBeenFocused) {
                        checkAndDisplayError()
                    } else {
                        hasBeenFocused = true
                        dismissError()
                    }
                },
            placeholder = placeholder,
            label = label,
            trailingIcon = trailingIcon,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            maxLine = 1,
            readOnly = readOnly,
            enabled = enabled,
            error = collectedError,
            interactionSource = null,
        )

        if (isPickerBottomSheetVisible) {
            focusManager.clearFocus(true)

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
                                    value = CountryFieldData(
                                        countryName = country.name,
                                        countryIsoName = country.isoName,
                                    )
                                    delegate.onCountrySelected(country)
                                    isPickerBottomSheetVisible = false
                                    dismissError()
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

        LaunchedEffect(Unit) {
            delegate.initWithInitialCountryName(collectedValue.countryName)
        }
    }

    companion object {
        fun initialValueFromRawCountryName(
            countryName: String,
        ): CountryFieldData {
            return Locale.getAvailableLocales().firstOrNull { it.displayCountry == countryName }?.let {
                return CountryFieldData(
                    countryName = countryName,
                    countryIsoName = it.isO3Country,
                )
            } ?: CountryFieldData("", "")
        }
    }
}
