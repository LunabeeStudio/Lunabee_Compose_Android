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
 * build.gradle.kts
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/21/2022 - for the Lunabee Compose library.
 */

plugins {
    id("lunabee.android-library-conventions")
    id("lunabee.library-publish-conventions")
}

android {
    namespace = "studio.lunabee.compose.theme"
    resourcePrefix("lbc_th_")

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

description = "Function to build dynamic theme with Material3 algorithm"
version = AndroidConfig.LBC_THEME_VERSION

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)

    implementation(project(Modules.MaterialColorUtilities))
}
