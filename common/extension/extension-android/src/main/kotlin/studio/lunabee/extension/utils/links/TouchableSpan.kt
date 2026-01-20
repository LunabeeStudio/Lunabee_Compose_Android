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

package studio.lunabee.extension.utils.links

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
