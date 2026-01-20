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

@file:Suppress("NOTHING_TO_INLINE")

// Aliases to other public API.

package com.lunabee.lbextensions.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.view.children
import androidx.core.view.contains
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.iterator
import androidx.core.view.minusAssign
import androidx.core.view.plusAssign
import androidx.core.view.setMargins
import androidx.core.view.size
import androidx.core.view.updateMargins

/**
 * Returns the view at [index].
 *
 * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to the count.
 */
@Deprecated("Use the core-ktx extension instead", replaceWith = ReplaceWith("get(index)", "androidx.core.view.get"))
operator fun ViewGroup.get(index: Int) = get(index)

/** Returns `true` if [view] is found in this view group. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("contains(view)", "androidx.core.view.contains"),
)
inline operator fun ViewGroup.contains(view: View) = contains(view)

/** Adds [view] to this view group. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("plusAssign(view)", "androidx.core.view.plusAssign"),
)
inline operator fun ViewGroup.plusAssign(view: View) = plusAssign(view)

/** Removes [view] from this view group. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("minusAssign(view)", "androidx.core.view.minusAssign"),
)
inline operator fun ViewGroup.minusAssign(view: View) = minusAssign(view)

/** Returns the number of views in this view group. */
@Deprecated("Use the core-ktx extension instead", replaceWith = ReplaceWith("size", "androidx.core.view.size"))
inline val ViewGroup.size: Int
    get() = size

/** Returns true if this view group contains no views. */
@Deprecated("Use the core-ktx extension instead", replaceWith = ReplaceWith("isEmpty()", "androidx.core.view.isEmpty"))
inline fun ViewGroup.isEmpty() = isEmpty()

/** Returns true if this view group contains one or more views. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("isNotEmpty()", "androidx.core.view.isNotEmpty"),
)
inline fun ViewGroup.isNotEmpty() = isNotEmpty()

/** Performs the given action on each view in this view group. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("forEach(action)", "androidx.core.view.forEach"),
)
inline fun ViewGroup.forEach(action: (view: View) -> Unit) {
    forEach(action)
}

/** Performs the given action on each view in this view group, providing its sequential index. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("forEachIndexed(action)", "androidx.core.view.forEachIndexed"),
)
inline fun ViewGroup.forEachIndexed(action: (index: Int, view: View) -> Unit) {
    forEachIndexed(action)
}

/** Returns a [MutableIterator] over the views in this view group. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("iterator()", "androidx.core.view.iterator"),
)
operator fun ViewGroup.iterator() = iterator()

/** Returns a [Sequence] over the child views in this view group. */
@Deprecated("Use the core-ktx extension instead", replaceWith = ReplaceWith("children", "androidx.core.view.children"))
val ViewGroup.children: Sequence<View>
    get() = children

/**
 * Sets the margins in the ViewGroup's MarginLayoutParams. This version of the method sets all axes
 * to the provided size.
 *
 * @see ViewGroup.MarginLayoutParams.setMargins
 */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("setMargins(size)", "androidx.core.view.setMargins"),
)
inline fun ViewGroup.MarginLayoutParams.setMargins(@Px size: Int) {
    setMargins(size)
}

/**
 * Updates the margins in the [ViewGroup]'s [ViewGroup.MarginLayoutParams].
 * This version of the method allows using named parameters to just set one or more axes.
 *
 * @see ViewGroup.MarginLayoutParams.setMargins
 */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("updateMargins(left, top, right, bottom)", "androidx.core.view.updateMargins"),
)
inline fun ViewGroup.MarginLayoutParams.updateMargins(
    @Px left: Int = leftMargin,
    @Px top: Int = topMargin,
    @Px right: Int = rightMargin,
    @Px bottom: Int = bottomMargin,
) {
    updateMargins(left, top, right, bottom)
}
