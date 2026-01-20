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

package com.lunabee.lbextensions.utils.links

import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

/**
 * [LinkTouchMovementMethod] subclass used to set link to TextView
 * Need to be used with a [TouchableSpan]
 */
class LinkTouchMovementMethod : LinkMovementMethod() {

    /**
     * The [TouchableSpan] currently clicked
     */
    private var pressedSpan: TouchableSpan? = null

    override fun onTouchEvent(widget: TextView?, buffer: Spannable?, event: MotionEvent?): Boolean {
        if (widget != null && buffer != null && event != null) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                pressedSpan = getPressedSpan(widget, buffer, event)
                pressedSpan?.let {
                    it.isPressed = true
                    Selection.setSelection(buffer, buffer.getSpanStart(it), buffer.getSpanEnd(it))
                }
            } else if (event.action == MotionEvent.ACTION_MOVE) {
                val touchableSpan = getPressedSpan(widget, buffer, event)
                if (pressedSpan != null && touchableSpan != pressedSpan) {
                    pressedSpan?.isPressed = false
                    pressedSpan = null
                    Selection.removeSelection(buffer)
                }
            } else {
                pressedSpan?.let {
                    it.isPressed = false
                    super.onTouchEvent(widget, buffer, event)
                }
                pressedSpan = null
                Selection.removeSelection(buffer)
            }
            return true
        }
        return super.onTouchEvent(widget, buffer, event)
    }

    private fun getPressedSpan(textView: TextView, buffer: Spannable, event: MotionEvent): TouchableSpan? {
        var x = event.x
        x -= textView.totalPaddingLeft
        x += textView.scrollX

        var y = event.y.toInt()
        y -= textView.totalPaddingTop
        y += textView.scrollY

        val layout = textView.layout
        val line = layout.getLineForVertical(y)
        val off = layout.getOffsetForHorizontal(line, x)

        return buffer.getSpans(off, off, TouchableSpan::class.java).firstOrNull()
    }
}
