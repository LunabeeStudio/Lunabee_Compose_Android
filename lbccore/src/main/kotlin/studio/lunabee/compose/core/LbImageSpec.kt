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
 * LbImageSpec.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/22/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.core

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface LbImageSpec {

    class Icon(
        @DrawableRes val drawableRes: Int,
        val tint: @Composable () -> Color = { Color.Unspecified },
    ) : LbImageSpec

    class KtImageVector(
        val icon: ImageVector,
        val tint: @Composable () -> Color = { Color.Unspecified },
    ) : LbImageSpec

    class ImageDrawable(
        @DrawableRes val drawableRes: Int,
    ) : LbImageSpec

    class Bitmap(
        val bitmap: android.graphics.Bitmap,
    ) : LbImageSpec

    class ByteArray(
        val byteArray: kotlin.ByteArray,
    ) : LbImageSpec

    class Url(
        val url: String,
    ) : LbImageSpec

    class Uri(
        val uri: android.net.Uri,
    ) : LbImageSpec
}
