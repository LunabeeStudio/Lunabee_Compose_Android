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
 *
 * @param mergeDescendants Whether the semantic information provided by the owning component
 * and its descendants should be treated as one logical entity. Most commonly set on screen-reader-focusable items such as buttons
 * or form fields. In the merged semantics tree, all descendant nodes (except those themselves marked mergeDescendants) will disappear
 * from the tree, and their properties will get merged into the parent's configuration (using a merging algorithm that varies based
 * on the type of property -- for example, text properties will get concatenated, separated by commas).
 *
 * @return current modifier with a [semantics] block added
 */
fun Modifier.addSemantics(
    accessibilityDescription: AccessibilityDescription,
    mergeDescendants: Boolean = false,
): Modifier {
    return semantics(mergeDescendants = mergeDescendants) {
        buildSemantics(accessibilityDescription = accessibilityDescription)
    }
}

/**
 * Set parameters for accessibility in replacement of system default.
 *
 * @param accessibilityDescription see [AccessibilityDescription]
 *
 * @return current modifier with a [clearAndSetSemantics] block added
 */
fun Modifier.overrideSemantics(
    accessibilityDescription: AccessibilityDescription,
): Modifier {
    return clearAndSetSemantics {
        buildSemantics(accessibilityDescription = accessibilityDescription)
    }
}

/**
 * Talkback will ignore elements marked with [invisibleToUser].
 */
@Suppress("unused")
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
 * @param currentCheckedStateValue current state of the [androidx.compose.material.Checkbox] (true/false)
 *
 * @param onCheckedChange trigger on user interaction
 *
 * @param accessibilityDescription see [AccessibilityDescription]
 *
 * @return a toggleable [Modifier] with accessibility set
 */
fun Modifier.addSemanticsForCheckBox(
    currentCheckedStateValue: Boolean,
    onCheckedChange: (newCheckedStateValue: Boolean) -> Unit,
    accessibilityDescription: AccessibilityDescription,
    mergeDescendants: Boolean = false,
): Modifier {
    return addSemantics(
        accessibilityDescription = accessibilityDescription,
        mergeDescendants = mergeDescendants,
    ).toggleable(
        value = currentCheckedStateValue,
        onValueChange = onCheckedChange,
        role = Role.Checkbox,
    ) // order is important, semantics must be set before toggleable
}

/**
 * Similar to [addSemanticsForCheckBox], but will override all existing Semantics and replace it by parameters you provide.
 *
 * @param currentCheckedStateValue current state of the [androidx.compose.material.Checkbox] (true/false)
 *
 * @param onCheckedChange trigger on user interaction
 *
 * @param see [AccessibilityDescription]
 *
 * For other parameters, see [addSemantics] methods.
 *
 * @return a toggleable [Modifier] with accessibility set
 */
fun Modifier.overrideSemanticsForCheckBox(
    currentCheckedStateValue: Boolean,
    onCheckedChange: (newCheckedStateValue: Boolean) -> Unit,
    accessibilityDescription: AccessibilityDescription,
): Modifier {
    return overrideSemantics(
        accessibilityDescription = accessibilityDescription,
    ).toggleable(
        value = currentCheckedStateValue,
        onValueChange = onCheckedChange,
        role = Role.Checkbox,
    ) // order is important, semantics must be set before toggleable
}

private fun SemanticsPropertyReceiver.buildSemantics(
    accessibilityDescription: AccessibilityDescription,
) {
    accessibilityDescription.text?.let { this.text = AnnotatedString(text = it) }
    accessibilityDescription.contentDescription?.let { this.contentDescription = it }
    accessibilityDescription.stateDescription?.let {
        this.stateDescription = if (it.currentStateValue) {
            it.stateEnabledDescription
        } else {
            it.stateDisabledDescription
        }
    }

    if (accessibilityDescription.isHeading) {
        heading()
    }
}
