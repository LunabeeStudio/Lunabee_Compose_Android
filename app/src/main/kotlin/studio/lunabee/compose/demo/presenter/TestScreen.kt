/*
 * Copyright (c) 2024 Lunabee Studio
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
 * TestScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/1/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.foundation.presenter.PresentScreen

@Composable
fun TestPresenterRoute(
    navigateBack: () -> Unit,
) {
    val navScope = TestNavScope(navigateBack = navigateBack)
    PresentScreen<TestNavScope, TestPresenter>(navScope)
}

@Composable
fun TestScreen(
    uiState: TestUiState
) {
    Column {
        Text(
            text = uiState.title,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = uiState.subtitle,
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = uiState.timer.toString()
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = uiState.onBackClick) {
            Text(text = "Finish")
        }
        Button(onClick = uiState.onTitleChange) {
            Text(text = "Change Title")
        }
    }
}