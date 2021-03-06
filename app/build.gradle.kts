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
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {

    compileSdk = AndroidConfig.COMPILE_SDK
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK

        versionCode = System.getenv(EnvConfig.ENV_VERSION_CODE)?.toInt() ?: AndroidConfig.VERSION_CODE
        versionName = System.getenv(EnvConfig.ENV_VERSION_NAME) ?: AndroidConfig.VERSION_NAME
    }

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "_"

    buildTypes {
        debug {
            // Uncomment the following line to use the flavor signing
            // signingConfig = null
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles += getDefaultProguardFile("proguard-android-optimize.txt")
            proguardFiles += File("proguard-rules.pro")
        }
    }

    lint {
        abortOnError = false
        checkDependencies = true
        checkGeneratedSources = false
        xmlOutput = file("${project.rootDir}/build/reports/lint/lint-report.xml")
        htmlOutput = file("${project.rootDir}/build/reports/lint/lint-report.html")
        lintConfig = file("${project.rootDir}/lint.xml")
    }
}

dependencies {
    implementation(Kotlin.stdlib.jdk8)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.core.ktx)

    implementation(Google.android.material)

    implementation(Google.accompanist.systemuicontroller)

    implementation(AndroidX.activity.compose)
    implementation(AndroidX.compose.foundation)
    implementation(AndroidX.compose.material)
    implementation(AndroidX.navigation.compose)

    implementation(project(Modules.lbctopappbar))
    implementation(project(Modules.lbcaccessibility))
    implementation(project(Modules.lbcmaterial))
}
