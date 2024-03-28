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
 * LbcHapticEffect.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 3/25/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.haptic

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

/**
 * List of effect that libs currently handles.
 * @see <a href="https://github.com/android/platform-samples/tree/main/samples/user-interface/haptics">Original code</a>
 */
sealed interface LbcHapticEffect {
    val hapticId: Int

    fun isSupported(vibrator: Vibrator): Boolean {
        return LbcHapticFeedback.isPrimitiveSupported(vibrator = vibrator, primitiveId = hapticId)
    }

    sealed interface Predefined : LbcHapticEffect {
        data object Tick : Predefined {
            @RequiresApi(Build.VERSION_CODES.Q)
            override val hapticId: Int = VibrationEffect.EFFECT_TICK
        }

        data object Click : Predefined {
            @RequiresApi(Build.VERSION_CODES.Q)
            override val hapticId: Int = VibrationEffect.EFFECT_CLICK
        }

        data object HeavyClick : Predefined {
            @RequiresApi(Build.VERSION_CODES.Q)
            override val hapticId: Int = VibrationEffect.EFFECT_HEAVY_CLICK
        }

        data object DoubleClick : Predefined {
            @RequiresApi(Build.VERSION_CODES.Q)
            override val hapticId: Int = VibrationEffect.EFFECT_DOUBLE_CLICK
        }
    }

    sealed interface FeedbackConstants : LbcHapticEffect {
        data object Confirm : FeedbackConstants {
            @RequiresApi(Build.VERSION_CODES.R)
            override val hapticId: Int = HapticFeedbackConstants.CONFIRM
        }

        data object Reject : FeedbackConstants {
            @RequiresApi(Build.VERSION_CODES.R)
            override val hapticId: Int = HapticFeedbackConstants.REJECT
        }
    }

    sealed interface CompositionPrimitives : LbcHapticEffect {
        data object LowTick : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.S)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_LOW_TICK
        }

        data object Tick : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.R)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_TICK
        }

        data object Click : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.R)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_CLICK
        }

        data object SlowRise : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.R)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_SLOW_RISE
        }

        data object QuickRise : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.R)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_QUICK_RISE
        }

        data object QuickFall : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.R)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_QUICK_FALL
        }

        data object Spin : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.S)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_SPIN
        }

        data object Thud : CompositionPrimitives {
            @RequiresApi(Build.VERSION_CODES.S)
            override val hapticId: Int = VibrationEffect.Composition.PRIMITIVE_THUD
        }
    }

    sealed interface Compose : LbcHapticEffect {
        val hapticFeedbackType: HapticFeedbackType

        data object LongPress : Compose {
            override val hapticId: Int = HapticFeedbackConstants.LONG_PRESS
            override val hapticFeedbackType: HapticFeedbackType = HapticFeedbackType.LongPress
        }

        data object TextHandleMove : Compose {
            @RequiresApi(Build.VERSION_CODES.O_MR1)
            override val hapticId: Int = HapticFeedbackConstants.TEXT_HANDLE_MOVE
            override val hapticFeedbackType: HapticFeedbackType = HapticFeedbackType.TextHandleMove
        }
    }
}
