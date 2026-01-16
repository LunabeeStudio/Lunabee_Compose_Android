package com.lunabee.lbextensions.view

import android.text.SpannableString
import android.widget.TextView
import com.lunabee.lbextensions.utils.links.LinkTouchMovementMethod
import com.lunabee.lbextensions.utils.links.TouchableSpan

/**
 * Set the textView text value and a clickable link into it
 * @param string the string to set in textView's text value
 * @param touchableSpan represents the link part of the text value
 */
fun TextView.setLink(string: String, touchableSpan: TouchableSpan) {
    setLinks(string, listOf(touchableSpan))
}

/**
 * Set the textView text value and multiple clickable link into it
 * @param string the string to set in textView's text value
 * @param touchableSpans list of TouchableSpan objects, represents the different link parts of the text value
 */
fun TextView.setLinks(string: String, touchableSpans: List<TouchableSpan>) {
    movementMethod = LinkTouchMovementMethod()
    val spannable = SpannableString(string)
    for (touchableSpan in touchableSpans) {
        spannable.setSpan(touchableSpan, touchableSpan.start, touchableSpan.end, 0)
    }
    setText(spannable, TextView.BufferType.SPANNABLE)
}
