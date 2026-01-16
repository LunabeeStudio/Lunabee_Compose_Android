@file:JvmName("ActivityUtils")

package com.lunabee.lbextensions

import android.app.Activity
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

/**
 *  Hide the keyboard if it was already shown.
 *  ## To hide the keyboard you need to have a focusable view in your layout
 *
 *  @receiver The activity where the keyboard will be hidden.
 */
fun Activity.hideSoftKeyBoard() {
    ContextCompat.getSystemService(this, InputMethodManager::class.java)?.let { imm ->
        var view = currentFocus
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = (findViewById(android.R.id.content) as? ViewGroup)?.rootView
        }
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
