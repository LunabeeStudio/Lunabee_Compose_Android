package com.lunabee.lbextensions.utils.links

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan

/**
 * [ClickableSpan] with press state and underline
 * This is used to put link into string
 * [start] and [end] define the start and end for this spannable
 * [normalTextColor] define the text color for normal state
 * [pressedTextColor] define the text color for pressed state
 * [pressedBackgroundColor] define the background color for pressed state, default value is transparent
 */
abstract class TouchableSpan(
    val start: Int,
    val end: Int,
    private val normalTextColor: Int,
    private val pressedTextColor: Int,
    private val pressedBackgroundColor: Int = Color.TRANSPARENT,
) : ClickableSpan() {

    /**
     * Public value to set the span's current state : pressed or not
     */
    var isPressed: Boolean = false

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        if (isPressed) {
            ds.color = pressedTextColor
            ds.bgColor = pressedBackgroundColor
        } else {
            ds.color = normalTextColor
            ds.bgColor = Color.TRANSPARENT
        }
        ds.isUnderlineText = true
    }
}
