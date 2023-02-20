/*
 * Copyright © 2023 Lunabee Studio
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
 * LbcAndroidTestConstants.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/20/2023 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.androidtest

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal object LbcAndroidTestConstants {
    /**
     * Default timeout used for waiting methods in a [androidx.compose.ui.test.ComposeUiTest] scope.
     */
    val WaitNodeTimeout: Duration = 5.seconds
}
