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
 * HapticScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/5/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.demo.haptic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.foundation.haptic.LbcHapticEffect
import studio.lunabee.compose.foundation.haptic.LbcHapticFeedback
import studio.lunabee.compose.foundation.haptic.rememberLbcHapticFeedback

@Composable
fun HapticScreen() {
    val lbcHapticFeedback: LbcHapticFeedback = rememberLbcHapticFeedback()
    val supportedEffect: List<LbcHapticEffect> =
        remember {
            lbcHapticFeedback
                .getSupportedHapticEffect()
        }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        items(items = LbcHapticFeedback.AllEffects) { effect ->
            Button(
                enabled = supportedEffect.contains(effect),
                onClick = { lbcHapticFeedback.perform(hapticEffect = effect) },
                modifier =
                Modifier
                    .padding(all = 8.dp)
            ) {
                Text(text = "${effect.javaClass.simpleName}\n(code = ${effect.hapticId})")
            }
        }
    }
}
