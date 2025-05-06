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
 * VisibilityFieldOption.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/10/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.field.text.option.password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.compose.foundation.uifield.UiFieldOption
import studio.lunabee.compose.foundation.uifield.composable.TrailingAction

context(PasswordVisibilityOptionHolder)
class PasswordVisibilityFieldOption : UiFieldOption {

    override fun onClick(): Unit = onVisibilityToggle()

    override val clickLabel: LbcTextSpec
        get() {
            return if (isValueVisible.value) {
                visibilityOptionData.hidePasswordClickLabel
            } else {
                visibilityOptionData.showPasswordClickLabel
            }
        }

    @Composable
    override fun Composable(modifier: Modifier) {
        val valueVisible by isValueVisible.collectAsState()
        val image = if (valueVisible) {
            visibilityOptionData.hideIcon
        } else {
            visibilityOptionData.showIcon
        }

        TrailingAction(
            image = image,
            onClick = ::onClick,
            modifier = modifier,
            contentDescription = clickLabel,
        )
    }
}
