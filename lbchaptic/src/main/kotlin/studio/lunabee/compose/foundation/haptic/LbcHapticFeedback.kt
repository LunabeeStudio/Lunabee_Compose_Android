/*
 * Copyright (c) 2024 Lunabee Studio
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
 *
 * LbcHapticFeedback.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 3/25/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.haptic

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.core.content.ContextCompat

class LbcHapticFeedback(
    val vibrator: Vibrator?,
    val hapticFeedBack: HapticFeedback,
    private val view: View,
) {
    /**
     * Perform an haptic feedback. If it's not supported, [fallback] will be used. To avoid an infinite loop, [fallback] is also tested.
     */
    fun perform(hapticEffect: LbcHapticEffect, fallback: LbcHapticEffect? = LbcHapticEffect.Compose.LongPress) {
        if (vibrator == null) return
        val supportedHapticEffect = getSupportedHapticEffect()
        (hapticEffect.takeIf { supportedHapticEffect.contains(it) } ?: fallback)?.let { effect ->
            when (effect) {
                is LbcHapticEffect.Compose -> performCompose(hapticEffect = effect)
                is LbcHapticEffect.CompositionPrimitives -> performCompositionPrimitives(effect)
                is LbcHapticEffect.Predefined -> performPredefined(effect)
                is LbcHapticEffect.FeedbackConstants -> performFeedbackConstants(view = view, hapticEffect = effect)
            }
        }
    }

    private fun performPredefined(hapticEffect: LbcHapticEffect.Predefined) {
        if (vibrator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator.vibrate(VibrationEffect.createPredefined(hapticEffect.hapticId))
        }
    }

    private fun performFeedbackConstants(view: View, hapticEffect: LbcHapticEffect.FeedbackConstants) {
        view.performHapticFeedback(hapticEffect.hapticId)
    }

    private fun performCompositionPrimitives(hapticEffect: LbcHapticEffect.CompositionPrimitives) {
        if (vibrator != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            vibrator.vibrate(VibrationEffect.startComposition().addPrimitive(hapticEffect.hapticId).compose())
        }
    }

    private fun performCompose(hapticEffect: LbcHapticEffect.Compose) {
        hapticFeedBack.performHapticFeedback(hapticEffect.hapticFeedbackType)
    }

    fun getSupportedHapticEffect(): List<LbcHapticEffect> {
        if (vibrator == null) return emptyList()
        return buildList {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                add(LbcHapticEffect.Compose.TextHandleMove)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                add(LbcHapticEffect.Predefined.Tick)
                add(LbcHapticEffect.Predefined.Click)
                add(LbcHapticEffect.Predefined.HeavyClick)
                add(LbcHapticEffect.Predefined.DoubleClick)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                add(LbcHapticEffect.FeedbackConstants.Confirm)
                add(LbcHapticEffect.FeedbackConstants.Reject)
                add(LbcHapticEffect.CompositionPrimitives.Tick)
                add(LbcHapticEffect.CompositionPrimitives.Click)
                add(LbcHapticEffect.CompositionPrimitives.SlowRise)
                add(LbcHapticEffect.CompositionPrimitives.QuickRise)
                add(LbcHapticEffect.CompositionPrimitives.QuickFall)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(LbcHapticEffect.CompositionPrimitives.LowTick)
                add(LbcHapticEffect.CompositionPrimitives.Spin)
                add(LbcHapticEffect.CompositionPrimitives.Thud)
            }
            add(LbcHapticEffect.Compose.LongPress)
            retainAll { it.isSupported(vibrator = vibrator) }
        }
    }

    companion object {
        val AllEffects: List<LbcHapticEffect> = listOf(
            LbcHapticEffect.Compose.TextHandleMove,
            LbcHapticEffect.Compose.LongPress,
            LbcHapticEffect.Predefined.Tick,
            LbcHapticEffect.Predefined.Click,
            LbcHapticEffect.Predefined.HeavyClick,
            LbcHapticEffect.Predefined.DoubleClick,
            LbcHapticEffect.FeedbackConstants.Confirm,
            LbcHapticEffect.FeedbackConstants.Reject,
            LbcHapticEffect.CompositionPrimitives.Tick,
            LbcHapticEffect.CompositionPrimitives.Click,
            LbcHapticEffect.CompositionPrimitives.SlowRise,
            LbcHapticEffect.CompositionPrimitives.QuickRise,
            LbcHapticEffect.CompositionPrimitives.QuickFall,
            LbcHapticEffect.CompositionPrimitives.LowTick,
            LbcHapticEffect.CompositionPrimitives.Spin,
            LbcHapticEffect.CompositionPrimitives.Thud,
        )

        /**
         * Query whether the vibrator supports all of the given primitives.
         * If a primitive is not supported by the device, then no vibration will occur if it is played.
         */
        fun isPrimitiveSupported(vibrator: Vibrator, primitiveId: Int): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            vibrator.areAllPrimitivesSupported(primitiveId)
        } else {
            false
        }
    }
}

/**
 * Create and remember an [LbcHapticFeedback] to execute vibration effect.
 * @param hapticFeedBack compose [HapticFeedback].
 * @param context current context to get a [Vibrator].
 */
@Composable
fun rememberLbcHapticFeedback(
    hapticFeedBack: HapticFeedback = LocalHapticFeedback.current,
    context: Context = LocalContext.current,
    view: View = LocalView.current,
): LbcHapticFeedback = remember {
    LbcHapticFeedback(
        hapticFeedBack = hapticFeedBack,
        vibrator = ContextCompat.getSystemService(context, Vibrator::class.java),
        view = view,
    )
}
