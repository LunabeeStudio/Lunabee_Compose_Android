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

package com.lunabee.lbextensions.view

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.shape.MaterialShapeDrawable

/**
 *  Hide the keyboard if it was already shown.
 *  ## To hide the keyboard, [this] should be in a focused layout
 *
 *  @receiver A view contained in a focused layout.
 */
fun View.hideSoftKeyBoard() {
    ContextCompat.getSystemService(context, InputMethodManager::class.java)?.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Set a window insets listener and consume insets.systemWindowInset
 */
fun View.consumeInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, _ ->
        WindowInsetsCompat.CONSUMED
    }
}

/**
 * Set a window insets listener and apply insets.systemWindowInsetBottom as bottom padding to the view and consume it
 */
fun View.applyAndConsumeWindowInsetBottom() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updatePadding(
            bottom = insets.getInsets(WindowInsetsCompat.Type.ime() or WindowInsetsCompat.Type.systemBars()).bottom,
        )
        WindowInsetsCompat.Builder(insets)
            .setInsets(
                WindowInsetsCompat.Type.systemBars(),
                Insets.of(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    0,
                ),
            )
            .build()
    }
}

/**
 * Create a [MaterialShapeDrawable] based on receiver view background color and [elevation] and set
 * it as background. This allows the dark theme elevation overlay.
 *
 * @param elevation Default elevation in px to set to the view
 */
fun View.colorBackgroundToMaterialShapeBackground(elevation: Float = 0f) {
    (background as? ColorDrawable)?.let {
        val materialShapeDrawable = MaterialShapeDrawable()
        materialShapeDrawable.initializeElevationOverlay(context)
        materialShapeDrawable.fillColor = ColorStateList.valueOf(it.color)
        materialShapeDrawable.elevation = elevation
        background = materialShapeDrawable
    }
}
