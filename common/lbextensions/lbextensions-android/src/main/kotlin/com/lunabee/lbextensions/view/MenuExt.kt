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

package com.lunabee.lbextensions.view

import android.app.SearchManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.core.view.children
import androidx.core.view.contains
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.iterator
import androidx.core.view.minusAssign
import androidx.core.view.size
import com.lunabee.lbextensions.content.colorFromAttribute

/**
 * Returns the menu at [index].
 *
 * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to the count.
 */
@Deprecated("Use the core-ktx extension instead", replaceWith = ReplaceWith("get(index)", "androidx.core.view.get"))
inline operator fun Menu.get(index: Int): MenuItem = get(index)

/** Returns `true` if [item] is found in this menu. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("contains(item)", "androidx.core.view.contains"),
)
operator fun Menu.contains(item: MenuItem): Boolean = contains(item)

/** Removes [item] from this menu. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("minusAssign(item)", "androidx.core.view.minusAssign"),
)
inline operator fun Menu.minusAssign(item: MenuItem) = minusAssign(item)

/** Returns the number of items in this menu. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("size", "androidx.core.view.size"),
)
inline val Menu.size: Int
    get() = this.size

/** Returns true if this menu contains no items. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("isEmpty()", "androidx.core.view.isEmpty"),
)
inline fun Menu.isEmpty() = isEmpty()

/** Returns true if this menu contains one or more items. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("isNotEmpty()", "androidx.core.view.isNotEmpty"),
)
inline fun Menu.isNotEmpty() = isNotEmpty()

/** Performs the given action on each item in this menu. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("forEach(action)", "androidx.core.view.forEach"),
)
inline fun Menu.forEach(action: (item: MenuItem) -> Unit) {
    forEach(action)
}

/** Performs the given action on each item in this menu, providing its sequential index. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("forEachIndexed(action)", "androidx.core.view.forEachIndexed"),
)
inline fun Menu.forEachIndexed(action: (index: Int, item: MenuItem) -> Unit) {
    forEachIndexed(action)
}

/** Returns a [MutableIterator] over the items in this menu. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("iterator", "androidx.core.view.iterator"),
)
operator fun Menu.iterator(): MutableIterator<MenuItem> = iterator()

/** Returns a [Sequence] over the items in this menu. */
@Deprecated(
    "Use the core-ktx extension instead",
    replaceWith = ReplaceWith("children", "androidx.core.view.children"),
)
val Menu.children: Sequence<MenuItem>
    get() = children

/**
 * Helper function that do all the work to initialize a search view.
 *
 * @param activity The activity where te want to display the seachview.
 * @param listener the listener object that receives callbacks when the user performs
 * actions in the SearchView such as clicking on buttons or typing a query.
 * @param maxWidth true to makes the view at most pixels wide.
 */
fun MenuItem.initSearch(
    activity: AppCompatActivity?,
    searchString: String,
    listener: SearchView.OnQueryTextListener,
    maxWidth: Boolean,
) {
    setOnActionExpandListener(
        object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                activity?.supportActionBar?.setDisplayShowTitleEnabled(false)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                activity?.supportActionBar?.setDisplayShowTitleEnabled(true)
                return true
            }
        },
    )

    val searchView = actionView as SearchView
    searchView.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    val searchManager = activity?.getSystemService<SearchManager>()

    icon?.mutate()?.let {
        val color = activity?.colorFromAttribute(android.R.attr.textColorPrimary) ?: Color.BLACK
        it.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    searchManager?.let {
        searchView.setSearchableInfo(it.getSearchableInfo(activity.componentName))
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(listener)

        if (maxWidth) {
            searchView.maxWidth = Int.MAX_VALUE
        }

        if (searchString.isNotEmpty()) {
            searchView.isIconified = false
            searchView.setQuery(searchString, true)
            searchView.clearFocus()
        }
    }
}
