/*
 * Copyright © 2022 Lunabee Studio
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
 * AndroidConfig.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

object AndroidConfig {
    const val VERSION_CODE: Int = 1
    const val VERSION_NAME: String = BuildConfigs.lunabeeCompose

    const val COMPILE_SDK: Int = BuildConfigs.compileSdk
    const val TARGET_SDK: Int = COMPILE_SDK
    const val MIN_SDK: Int = BuildConfigs.minSdk
    const val BUILD_TOOLS_VERSION: String = "33.0.0"

    const val LIBRARY_URL: String = "https://github.com/LunabeeStudio/Lunabee_Compose_Android"
    const val GROUP_ID: String = "studio.lunabee.compose"

    const val LBC_CORE_VERSION: String = "1.0.0"
    const val LBC_FOUNDATION_VERSION: String = "1.0.0"
    const val LBC_ACCESSIBILITY_VERSION: String = "1.4.0"
    const val LBC_THEME_VERSION: String = "1.0.0"
    const val MATERIAL_COLOR_UTILITIES_VERSION: String = "1.0.0"
}
