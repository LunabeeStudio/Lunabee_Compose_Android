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

package studio.lunabee.compose.demo.presenter.simple

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.common.CoreDemoBox
import studio.lunabee.compose.common.CorePreview

@Composable
fun SimpleExampleScreen(
    uiState: SimpleExampleUiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Checkbox(
            checked = uiState.isChecked,
            onCheckedChange = { newCheckedValue ->
                uiState.onToggleClick(newCheckedValue)
            },
        )
        Text(uiState.text)
        Button(onClick = uiState.onNewValue) {
            Text("Show Change value")
        }
        Button(onClick = uiState.onShowToastClick) {
            Text("Show Toast From Reducer")
        }
        Button(onClick = uiState.onShowCascadeToastClick) {
            Text("Cascade use activity toast")
        }
    }
}

@CorePreview
@Composable
private fun SimpleExampleScreenPreview() {
    CoreDemoBox {
        SimpleExampleScreen(
            uiState = SimpleExampleUiState(
                onToggleClick = {},
                onNewValue = {},
                onShowToastClick = {},
                isChecked = true,
                text = "Hello Preview",
                onShowCascadeToastClick = {},
            ),
        )
    }
}
