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
 * LbCropConst.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/24/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbccrop

object LbCropConst {
    /**
     * Temporary file the LbCropView will work with. It will hold the currently displayed image.
     */
    const val TempFile: String = "lb_crop_temp_file.jpg"

    /**
     * Delay before checking if the zoom level needs to be adjust (so that the image fits the bounds).
     */
    const val RepositionningDelayMs: Long = 300

    /**
     * Angle to add at each image rotation.
     */
    const val RotationAngleIncrement: Float = 90f

    /**
     * Safe guard to avoid a blocking of the zoom view
     */
    const val MaxZoomSafeGard: Float = 1.05f
}
