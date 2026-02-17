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

package studio.lunabee.compose.foundation.uifield

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle

object TextFieldValueUtils {
    fun saveToSavedStateHandle(id: String, value: TextFieldValue, savedStateHandle: SavedStateHandle) {
        savedStateHandle["${id}_text"] = value.text
        savedStateHandle["${id}_selStart"] = value.selection.start
        savedStateHandle["${id}_selEnd"] = value.selection.end
    }

    fun restoreFromSavedStateHandle(id: String, savedStateHandle: SavedStateHandle): TextFieldValue? {
        val text = savedStateHandle.get<String>("${id}_text")
        val start = savedStateHandle["${id}_selStart"] ?: 0
        val end = savedStateHandle["${id}_selEnd"] ?: 0

        return text?.let {
            TextFieldValue(
                text = it,
                selection = TextRange(start, end),
            )
        }
    }
}
