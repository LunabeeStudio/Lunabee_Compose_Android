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
 * StateDescription.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/10/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcaccessibility.model

/**
 * Wrapper object to set a state description in a [androidx.compose.ui.Modifier] with semantics method.
 *
 * @param stateEnabledDescription string that will be read by Talkback when your state is true/enabled/checked...
 * @param stateDisabledDescription string that will be read by Talkback when your state is false/disabled/unchecked...
 */
data class StateDescription(
    val stateEnabledDescription: String,
    val stateDisabledDescription: String,
)
