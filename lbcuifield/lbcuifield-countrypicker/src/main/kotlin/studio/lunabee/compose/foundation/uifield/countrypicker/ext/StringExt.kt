/*
 * Copyright (c) 2025 Lunabee Studio
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
 * StringExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/25/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.foundation.uifield.countrypicker.ext

import doist.x.normalize.Form
import doist.x.normalize.normalize

internal fun String.normalized(): String =
    this
        .lowercase()
        .normalize(Form.NFD)
        .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
