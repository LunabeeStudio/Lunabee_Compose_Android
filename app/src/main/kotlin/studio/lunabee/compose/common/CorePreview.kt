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
 * PreviewUtils.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/22/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class CorePreview

@Composable
fun CoreDemoBox(content: @Composable (BoxScope.() -> Unit)) {
    AppDemoTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background), content = content)
    }
}
