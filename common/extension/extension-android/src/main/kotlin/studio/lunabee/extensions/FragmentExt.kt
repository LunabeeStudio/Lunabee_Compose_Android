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

package studio.lunabee.extension

import androidx.annotation.PluralsRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope

val Fragment.viewLifecycleScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope

/**
 *  Hide the keyboard if it was already shown.
 *  ## To hide the keyboard you need to have a focusable view in your layout
 *
 *  @receiver The fragment attach to the activity where the keyboard will be hidden.
 */
fun Fragment.hideSoftKeyBoard() {
    activity?.hideSoftKeyBoard()
}

/**
 * @see android.content.res.Resources.getQuantityString
 */
fun Fragment.getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String =
    resources.getQuantityString(id, quantity, *formatArgs)
