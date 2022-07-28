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
 * LbcSearchTextFieldForTopAppBar.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/17/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction

/**
 * A standard implementation for a search text field that can be used in [LbcSearchTopAppBar]
 * @param searchTextFieldValue current value of the [BasicTextField], use a [TextFieldValue]
 * @param onTextFieldValueChanged called when user edits the field
 * @param clearSearchAction describe the action displayed at the end of the [LbcSearchTextField]
 * @param onKeyboardSearchAction action executed when user uses IME option of the keyboard
 * @param focusRequester provide your own FocusRequested if you want to automatically focus the [LbcSearchTextField]
 * @param keyboardOptions set your IME option. By default, we use [ImeAction.Search]
 * @param keyboardActions set your custom keyboard action. By default, we execute [onKeyboardSearchAction]
 * @param textStyle custom text field style.
 * @param cursorBrush custom cursor style
 * @param visualTransformation if you need to apply a visual transformation on input text.
 * @param placeholder default placeholder displayed when [LbcSearchTextField] is empty.
 */
@Composable
fun LbcSearchTextField(
    searchTextFieldValue: TextFieldValue,
    onTextFieldValueChanged: (newTextFieldValue: TextFieldValue) -> Unit,
    clearSearchAction: LbcTopAppBarAction,
    modifier: Modifier = Modifier,
    onKeyboardSearchAction: (() -> Unit)? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions(onSearch = { onKeyboardSearchAction?.invoke() }),
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)),
    cursorBrush: Brush = SolidColor(value = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholder: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = searchTextFieldValue,
            onValueChange = onTextFieldValueChanged,
            modifier = Modifier
                .weight(weight = 1f)
                .focusRequester(focusRequester = focusRequester),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            textStyle = textStyle,
            cursorBrush = cursorBrush,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                if (searchTextFieldValue.text.isEmpty()) {
                    placeholder?.invoke()
                }
                innerTextField() // mandatory in all cases
            }
        )

        LbcMenuIconButton(action = clearSearchAction)
    }
}
