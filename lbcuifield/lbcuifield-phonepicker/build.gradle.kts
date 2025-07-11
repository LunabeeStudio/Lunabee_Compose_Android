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
 * Created by Lunabee Studio / Date - 7/29/2022 - for the Lunabee Compose library.
 */

plugins {
    id("lunabee.android-library-conventions")
    id("lunabee.library-publish-conventions")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    resourcePrefix("lbc_uifield_")
    namespace = "studio.lunabee.compose.uifield.phonepicker"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

description = "phone picker ui field with a way to search for country phone code"
version = AndroidConfig.LBCUIFIELD_PHONEPICKER_VERSION

dependencies {
    coreLibraryDesugaring(libs.desugarJdk)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(project(Modules.LbcCore))
    implementation(libs.kotlinx.serialization.json)
    implementation(project(Modules.LbcImage))
    implementation(project(Modules.LbcUiFieldCore))
    implementation(project(Modules.LbcUiFieldCountryPicker))
    implementation(libs.hbb20.countrycodepicker)
    implementation(libs.libphonenumber)
    implementation(libs.normalize)
}
