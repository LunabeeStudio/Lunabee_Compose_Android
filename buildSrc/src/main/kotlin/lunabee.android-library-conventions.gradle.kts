/*
 * Copyright (c) 2026 Lunabee Studio
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
 */

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.dokka")
}

// FIXME workaround https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
val libs: LibrariesForLibs = the<LibrariesForLibs>()

android {
    compileSdk = AndroidConfig.CompileSdk

    defaultConfig {
        minSdk = AndroidConfig.MinSdk
        @Suppress("DEPRECATION") // https://stackoverflow.com/questions/76084080/apply-targetsdk-in-android-instrumentation-test
        targetSdk = AndroidConfig.TargetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.JDK_VERSION
        targetCompatibility = AndroidConfig.JDK_VERSION
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    androidTestImplementation(libs.androidxTestRunner)
}

kotlin.compilerOptions.jvmTarget.set(AndroidConfig.JVM_TARGET)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AndroidConfig.JDK_VERSION.toString()))
    }
}
