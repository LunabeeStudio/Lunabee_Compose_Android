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
 * AccessibilityDescription.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/11/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcaccessibility.model

import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.heading

/**
 * Object use to set parameters accessibility.
 *
 * @param text see [SemanticsPropertyReceiver.text], text of the semantics node.
 * It must be real text instead of developer-set content description.
 *
 * @param contentDescription see [SemanticsPropertyReceiver.contentDescription], Developer-set content description of the semantics node.
 * If this is not set, accessibility services will present the [text] of this node as the content. This typically should not be set
 * directly by applications, because some screen readers will cease presenting other relevant information when this property is present.
 * This is intended to be used via Foundation components which are inherently intractable to automatically describe, such as Image, Icon,
 * and Canvas.
 *
 * @param stateDescription see [SemanticsPropertyReceiver.stateDescription], developer-set state description of the semantics node.
 * For example: on/off. If this not set, accessibility services will derive the state from other semantics properties,
 * like ProgressBarRangeInfo, but it is not guaranteed and the format will be decided by accessibility services.
 *
 * @param isHeading indicates if your Composable should be consider as a heading. This will facilitate navigation for the user. See
 * [heading] method.
 */
data class AccessibilityDescription(
    val text: String? = null,
    val contentDescription: String? = null,
    val stateDescription: StateDescription? = null,
    val isHeading: Boolean = false,
)
