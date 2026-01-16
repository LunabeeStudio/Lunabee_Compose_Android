@file:Suppress("unused")
@file:JvmName("TypedArrayUtils")

package com.lunabee.lbextensions.content.res

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.AnyRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.RequiresApi
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources

private fun TypedArray.checkAttribute(@StyleableRes index: Int) {
    require(hasValue(index)) { "Attribute not defined in set." }
}

/**
 * Retrieve the boolean value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getBoolean
 */
fun TypedArray.getBooleanOrThrow(@StyleableRes index: Int): Boolean {
    checkAttribute(index)
    return getBoolean(index, false)
}

/**
 * Retrieve the color value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getColor
 */
@ColorInt
fun TypedArray.getColorOrThrow(@StyleableRes index: Int): Int {
    checkAttribute(index)
    return getColor(index, 0)
}

/**
 * Retrieve the color state list value for the attribute at [index] or throws
 * [IllegalArgumentException] if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getColorStateList
 */
fun TypedArray.getColorStateListOrThrow(@StyleableRes index: Int): ColorStateList {
    checkAttribute(index)
    return checkNotNull(getColorStateList(index)) {
        "Attribute value was not a color or color state list."
    }
}

/**
 * Retrieve the color state list value for the attribute at [index] or throws
 * [IllegalArgumentException] if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getColorStateList
 */
fun TypedArray.getColorStateList(context: Context, @StyleableRes index: Int): ColorStateList? {
    checkAttribute(index)
    if (hasValue(index)) {
        val resourceId = getResourceId(index, 0)
        if (resourceId != 0) {
            val value = AppCompatResources.getColorStateList(context, resourceId)
            if (value != null) {
                return value
            }
        }
    }
    return getColorStateList(index)
}

/**
 * Retrieve the dimension value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getDimension
 */
fun TypedArray.getDimensionOrThrow(@StyleableRes index: Int): Float {
    checkAttribute(index)
    return getDimension(index, 0f)
}

/**
 * Retrieve the dimension pixel offset value for the attribute at [index] or throws
 * [IllegalArgumentException] if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getDimensionPixelOffset
 */
@Dimension
fun TypedArray.getDimensionPixelOffsetOrThrow(@StyleableRes index: Int): Int {
    checkAttribute(index)
    return getDimensionPixelOffset(index, 0)
}

/**
 * Retrieve the dimension pixel size value for the attribute at [index] or throws
 * [IllegalArgumentException] if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getDimensionPixelSize
 */
@Dimension
fun TypedArray.getDimensionPixelSizeOrThrow(@StyleableRes index: Int): Int {
    checkAttribute(index)
    return getDimensionPixelSize(index, 0)
}

/**
 * Retrieve the drawable value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getDrawable
 */
fun TypedArray.getDrawableOrThrow(@StyleableRes index: Int): Drawable? {
    checkAttribute(index)
    return getDrawable(index)
}

/**
 * Returns the drawable object from the given attributes.
 *
 *
 * This method supports inflation of `<vector>` and `<animated-vector>` resources
 * on devices where platform support is not available.
 */
fun TypedArray.getDrawable(
    context: Context, @StyleableRes index: Int,
): Drawable? {
    if (hasValue(index)) {
        val resourceId = getResourceId(index, 0)
        if (resourceId != 0) {
            val value = AppCompatResources.getDrawable(context, resourceId)
            if (value != null) {
                return value
            }
        }
    }
    return getDrawable(index)
}

/**
 * Retrieve the float value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getFloat
 */
fun TypedArray.getFloatOrThrow(@StyleableRes index: Int): Float {
    checkAttribute(index)
    return getFloat(index, 0f)
}

/**
 * Retrieve the font value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getFont
 */
@RequiresApi(26)
fun TypedArray.getFontOrThrow(@StyleableRes index: Int): Typeface? {
    checkAttribute(index)
    return getFont(index)
}

/**
 * Retrieve the integer value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getInt
 */
fun TypedArray.getIntOrThrow(@StyleableRes index: Int): Int {
    checkAttribute(index)
    return getInt(index, 0)
}

/**
 * Retrieve the integer value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getInteger
 */
fun TypedArray.getIntegerOrThrow(@StyleableRes index: Int): Int {
    checkAttribute(index)
    return getInteger(index, 0)
}

/**
 * Retrieves the resource identifier for the attribute at [index] or throws
 * [IllegalArgumentException] if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getResourceId
 */
@AnyRes
fun TypedArray.getResourceIdOrThrow(@StyleableRes index: Int): Int {
    checkAttribute(index)
    return getResourceId(index, 0)
}

/**
 * Retrieve the string value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getString
 */
fun TypedArray.getStringOrThrow(@StyleableRes index: Int): String {
    checkAttribute(index)
    return checkNotNull(getString(index)) {
        "Attribute value could not be coerced to String."
    }
}

/**
 * Retrieve the text value for the attribute at [index] or throws [IllegalArgumentException]
 * if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getText
 */
fun TypedArray.getTextOrThrow(@StyleableRes index: Int): CharSequence {
    checkAttribute(index)
    return checkNotNull(getText(index)) {
        "Attribute value could not be coerced to CharSequence."
    }
}

/**
 * Retrieve the text array value for the attribute at [index] or throws
 * [IllegalArgumentException] if not defined.
 *
 * @see TypedArray.hasValue
 * @see TypedArray.getTextArray
 */
fun TypedArray.getTextArrayOrThrow(@StyleableRes index: Int): Array<CharSequence> {
    checkAttribute(index)
    return getTextArray(index)
}

/**
 * Executes the given [block] function on this TypedArray and then recycles it.
 *
 * @see kotlin.io.use
 */
inline fun <R> TypedArray.use(block: (TypedArray) -> R): R {
    return block(this).also {
        recycle()
    }
}
