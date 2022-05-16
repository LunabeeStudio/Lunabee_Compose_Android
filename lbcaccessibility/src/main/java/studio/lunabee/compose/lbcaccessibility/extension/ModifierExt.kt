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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import studio.lunabee.compose.lbcaccessibility.model.AccessibilityDescription

/**
 * Set parameters for accessibility, in addition of system default.
 *
 * @param accessibilityDescription see [AccessibilityDescription]
 * @param mergeDescendants Whether the semantic information provided by the owning component
 * and its descendants should be treated as one logical entity. Most commonly set on screen-reader-focusable items such as buttons
 * or form fields. In the merged semantics tree, all descendant nodes (except those themselves marked mergeDescendants) will disappear
 * from the tree, and their properties will get merged into the parent's configuration (using a merging algorithm that varies based
 * on the type of property -- for example, text properties will get concatenated, separated by commas).
 * @param currentStateValue will be used if you set [AccessibilityDescription.stateDescription], will be ignored otherwise.
 *
 * @return current modifier with a [semantics] block added
 */
fun Modifier.addSemantics(
    accessibilityDescription: AccessibilityDescription,
    mergeDescendants: Boolean = false,
    currentStateValue: Boolean = false,
): Modifier {
    return semantics(mergeDescendants = mergeDescendants) {
        buildSemantics(
            accessibilityDescription = accessibilityDescription,
            currentStateValue = currentStateValue,
        )
    }
}

/**
 * Set parameters for accessibility in replacement of system default.
 *
 * @param accessibilityDescription see [AccessibilityDescription]
 * @param currentStateValue will be used if you set [AccessibilityDescription.stateDescription], will be ignored otherwise.
 *
 * @return current modifier with a [clearAndSetSemantics] block added
 */
fun Modifier.overrideSemantics(
    accessibilityDescription: AccessibilityDescription,
    currentStateValue: Boolean = false,
): Modifier {
    return clearAndSetSemantics {
        buildSemantics(
            accessibilityDescription = accessibilityDescription,
            currentStateValue = currentStateValue,
        )
    }
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

/**
 * [Modifier] to use on a container with a [androidx.compose.material.Checkbox] in order to make it accessible.
 * You can either use this [Modifier], or directly use [studio.lunabee.compose.lbcaccessibility.composable.AccessibilityCheckBoxRow] or
 * [studio.lunabee.compose.lbcaccessibility.composable.AccessibilityCheckBoxRowStateless] if you want to handle checked state.
 *
 * @param currentStateValue current state of the [androidx.compose.material.Checkbox] (true/false)
 * @param onStateChanged trigger on user interaction
 * @param accessibilityDescription see [AccessibilityDescription]
 *
 * @return a toggleable [Modifier] with accessibility set
 */
fun Modifier.addSemanticsToggleable(
    currentStateValue: Boolean,
    onStateChanged: (newStateValue: Boolean) -> Unit,
    accessibilityDescription: AccessibilityDescription,
    role: Role,
    mergeDescendants: Boolean = false,
): Modifier {
    return addSemantics(
        accessibilityDescription = accessibilityDescription,
        mergeDescendants = mergeDescendants,
        currentStateValue = currentStateValue,
    ).toggleable(
        value = currentStateValue,
        onValueChange = onStateChanged,
        role = role,
    ) // order is important, semantics must be set before toggleable
}

/**
 * Similar to [addSemanticsToggleable], but will override all existing Semantics and replace it by parameters you provide.
 * @param currentStateValue current state of the [androidx.compose.material.Checkbox] (true/false)
 * @param onStateChanged trigger on user interaction
 * @param accessibilityDescription see [AccessibilityDescription]
 *
 * @return a toggleable [Modifier] with accessibility set
 */
fun Modifier.overrideSemanticsToggleable(
    currentStateValue: Boolean,
    onStateChanged: (newStateValue: Boolean) -> Unit,
    accessibilityDescription: AccessibilityDescription,
    role: Role,
): Modifier {
    return overrideSemantics(
        accessibilityDescription = accessibilityDescription,
    ).toggleable(
        value = currentStateValue,
        onValueChange = onStateChanged,
        role = role,
    ) // order is important, semantics must be set before toggleable
}

private fun SemanticsPropertyReceiver.buildSemantics(
    accessibilityDescription: AccessibilityDescription,
    currentStateValue: Boolean,
) {
    accessibilityDescription.text?.let { this.text = AnnotatedString(text = it) }
    accessibilityDescription.contentDescription?.let { this.contentDescription = it }
    accessibilityDescription.stateDescription?.let {
        this.stateDescription = if (currentStateValue) {
            it.stateEnabledDescription
        } else {
            it.stateDisabledDescription
        }
    }

    if (accessibilityDescription.isHeading) {
        heading()
    }
}
