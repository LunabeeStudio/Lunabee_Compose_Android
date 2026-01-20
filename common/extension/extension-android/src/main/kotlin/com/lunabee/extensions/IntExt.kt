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

@file:Suppress("unused")
@file:JvmName("IntUtils")

package com.lunabee.lbextensions

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat

/**
 * Return the color corresponding to the current theme of the receiver id.
 *
 * @param context context where the theme is.
 * @return A color Int of the color.
 */
@ColorInt
fun @receiver:ColorRes Int.toColor(context: Context): Int = ContextCompat.getColor(context, this)

/**
 * Return the drawable corresponding to the current theme of the receiver id.
 *
 * @param context context where the theme is.
 * @return The asked drawable, null otherwise.
 */
fun @receiver:DrawableRes Int.toDrawable(context: Context): Drawable? = ContextCompat.getDrawable(
    context,
    this,
)

/**
 * Return the dimension corresponding to the current theme of the receiver attr id.
 *
 * @param context context where the theme is.
 * @return the dimension in pixels.
 */
@Px
fun @receiver:AttrRes Int.resolveAttributeToDimensPixelSize(context: Context): Int = context.resources.getDimensionPixelSize(
    this.resolveAttribute(context),
)

/**
 * Return the value corresponding to the current theme of the receiver attr id.
 *
 * @param context context where the theme is.
 * @return the value as Int
 */
fun @receiver:AttrRes Int.resolveAttributeToInt(context: Context): Int =
    context.resources.getInteger(this.resolveAttribute(context))

/**
 * Return the value corresponding to the current theme of the receiver attr id.
 *
 * @param context context where the theme is.
 * @return the value as drawable
 */
fun @receiver:AttrRes Int.resolveAttributeToDrawable(context: Context): Drawable? =
    ContextCompat.getDrawable(context, this.resolveAttribute(context))

/**
 * Return the dimension corresponding to the current theme of the receiver id.
 *
 * @param context context where the theme is.
 * @return the dimension in pixels.
 */
@Px
fun @receiver:DimenRes Int.toDimensPixelSize(context: Context): Int = context.resources.getDimensionPixelSize(
    this,
)

/**
 * Return the dimension corresponding to the current theme of the receiver id.
 *
 * @param context context where the theme is.
 * @return Resource dimension value multiplied by the appropriate metric.
 */
@Dimension
fun @receiver:DimenRes Int.toDimensSize(context: Context): Float = context.resources.getDimension(
    this,
)

/**
 * Return the id of the resource from the receiver id.
 *
 * @param context context where the theme is.
 * @return id of the resource.
 */
fun @receiver:AttrRes Int.resolveAttribute(context: Context): Int {
    val outValue = TypedValue()
    context.theme.resolveAttribute(this, outValue, true)
    return outValue.resourceId
}

/**
 * Return the id of the resource from the receiver id or null if the resource is not found
 *
 * @param context context where the theme is.
 * @return id of the resource.
 */
fun @receiver:AttrRes Int.resolveAttributeOrNull(context: Context): Int? {
    val outValue = TypedValue()
    return try {
        if (context.theme.resolveAttribute(this, outValue, true)) {
            outValue.resourceId
        } else {
            null
        }
    } catch (e: Resources.NotFoundException) {
        null
    }
}

/**
 * Return the color corresponding to theme of android from the receiver id.
 *
 * @param context context where the theme is.
 * @return A color Int of the color.
 */
@ColorInt
fun @receiver:AttrRes Int.fetchSystemColor(context: Context): Int {
    val typedValue = TypedValue()
    val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(this))
    val color = a.getColor(0, 0)

    a.recycle()

    return color
}

/**
 * Return the state aware colors corresponding to theme of android from the receiver id.
 *
 * @param context context where the theme is.
 * @return The ColorStateList.
 */
fun @receiver:AttrRes Int.fetchSystemColorStateList(context: Context): ColorStateList {
    val typedValue = TypedValue()
    val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(this))
    val colorStateList: ColorStateList = a.getColorStateList(0)!!
    a.recycle()
    return colorStateList
}
