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
 * ModifierExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/10/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbcaccessibility.extension

import androidx.compose.foundation.selection.toggleable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import studio.lunabee.compose.lbcaccessibility.model.ClickDescription
import studio.lunabee.compose.lbcaccessibility.model.ToggleDescription

/**
 * Set accessibility details for screen reader.
 *
 * @param text text see SemanticsPropertyReceiver.text, text of the semantics node.
 * It must be real text instead of developer-set content description.
 * @param contentDescription see SemanticsPropertyReceiver.contentDescription.
 * Developer-set content description of the semantics node. If this is not set, accessibility services will present the [text] of this node
 * as the content. This typically should not be set directly by applications, because some screen readers will cease presenting
 * other relevant information when this property is present. This is intended to be used via Foundation components which are inherently
 * intractable to automatically describe, such as Image, Icon, and Canvas.
 * @param stateDescription see SemanticsPropertyReceiver.stateDescription. Developer-set state description of the semantics node.
 * For example: on/off. If this not set, accessibility services will derive the state from other semantics properties,
 * like ProgressBarRangeInfo, but it is not guaranteed and the format will be decided by accessibility services.
 * @param clickDescription will be used for elements with no clickable method on [Modifier]
 * @param toggleDescription will be used for toggleable elements (Switch, CheckBox...)
 * @param mergeDescendants accessibility services will select only the merged element, and all semantics properties
 * of the descendants are merged.
 * @param isHeading indicates if your Composable should be consider as a heading. This will facilitate navigation for the user. See
 * [heading] method.
 * @param liveRegionMode the mode of live region. Live region indicates to accessibility services they should
 * automatically notify the user about changes to the node's content description or text, or to
 * the content descriptions or text of the node's children (where applicable).
 * @param clearBeforeSet whether it used [semantics] or [clearAndSetSemantics]. When using [clearAndSetSemantics],
 * [mergeDescendants] is ignored
 * @param setAsInvisible make node not selectable. It can be useful for example when using [liveRegionMode].
 */
fun Modifier.setAccessibilityDetails(
    text: String? = null,
    contentDescription: String? = null,
    stateDescription: String? = null,
    clickDescription: ClickDescription? = null,
    toggleDescription: ToggleDescription? = null,
    mergeDescendants: Boolean = false,
    isHeading: Boolean = false,
    liveRegionMode: LiveRegionMode? = null,
    clearBeforeSet: Boolean = false,
    setAsInvisible: Boolean = false,
): Modifier {
    val buildSemantics: SemanticsPropertyReceiver.() -> Unit = {
        buildSemantics(
            text = text,
            contentDescription = contentDescription,
            stateDescription = stateDescription,
            clickDescription = clickDescription,
            isHeading = isHeading,
            liveRegionMode = liveRegionMode,
            setAsInvisible = setAsInvisible,
        )
    }

    return if (clearBeforeSet) {
        clearAndSetSemantics(properties = buildSemantics)
    } else {
        semantics(mergeDescendants = mergeDescendants, properties = buildSemantics)
    }.then(
        // Toggleable must be set after semantics
        other = toggleDescription?.let {
            toggleable(
                value = toggleDescription.value,
                onValueChange = toggleDescription.onValueChanged,
                role = toggleDescription.role,
            )
        } ?: Modifier
    )
}

/**
 * Talkback will ignore elements marked with [invisibleToUser].
 */
@ExperimentalComposeUiApi
fun Modifier.setAsInvisibleForAccessibility(): Modifier {
    return semantics {
        invisibleToUser()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun SemanticsPropertyReceiver.buildSemantics(
    text: String?,
    contentDescription: String?,
    stateDescription: String?,
    clickDescription: ClickDescription?,
    isHeading: Boolean,
    liveRegionMode: LiveRegionMode?,
    setAsInvisible: Boolean,
) {
    if (isHeading) heading()
    if (setAsInvisible) invisibleToUser()
    text?.let { this.text = AnnotatedString(text = text) }
    contentDescription?.let { this.contentDescription = contentDescription }
    stateDescription?.let { this.stateDescription = stateDescription }
    clickDescription?.let { onClick(label = clickDescription.clickLabel, action = clickDescription.action) }
    liveRegionMode?.let { this.liveRegion = liveRegionMode }
}
