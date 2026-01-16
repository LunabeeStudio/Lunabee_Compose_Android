package com.lunabee.lbextensions

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
