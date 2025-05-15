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
 * lunabee.android-library-conventions.gradle.kts
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 1/25/2024 - for the Lunabee Compose library.
 */

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = AndroidConfig.GROUP_ID

// FIXME workaround https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
val libs: LibrariesForLibs = the<LibrariesForLibs>()

android {
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
        @Suppress("DEPRECATION") // https://stackoverflow.com/questions/76084080/apply-targetsdk-in-android-instrumentation-test
        targetSdk = AndroidConfig.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.JDK_VERSION
        targetCompatibility = AndroidConfig.JDK_VERSION
    }

    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
    }
}

dependencies {
    androidTestImplementation(libs.androidx.test.runner)
}

kotlin.compilerOptions.jvmTarget.set(AndroidConfig.JVM_TARGET)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AndroidConfig.JDK_VERSION.toString()))
    }
}
