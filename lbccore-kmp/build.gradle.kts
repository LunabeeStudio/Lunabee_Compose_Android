@file:OptIn(ExperimentalWasmDsl::class, ExperimentalComposeLibrary::class)

import org.gradle.kotlin.dsl.compose
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

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
 * Created by Lunabee Studio / Date - 11/30/2022 - for the Lunabee Compose library.
 */

plugins {
    id("com.android.library")
    id("lunabee.library-publish-conventions")
    alias(libs.plugins.compose)
    id(libs.plugins.compose.plugin.get().pluginId)
    id(libs.plugins.multiplatform.get().pluginId)
}

android {
    resourcePrefix("lbc_core_kmp_")
    namespace = "studio.lunabee.compose.core.kmp"

    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.JDK_VERSION
        targetCompatibility = AndroidConfig.JDK_VERSION
    }

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AndroidConfig.JDK_VERSION.toString()))
    }
}

compose.resources {
    packageOfResClass = "studio.lunabee.compose.core.kmp.generated.resources"
}

description = "A set of tools for Compose Multiplatform"
version = MultiplatformConfig.LBCCORE_KMP_VERSION

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }
    androidTarget()

    sourceSets {
        androidInstrumentedTest.dependencies {
            implementation(libs.compose.ui.test)
            implementation(libs.compose.ui.test.junit4)
            implementation(libs.androidx.test.runner)
            implementation(libs.kotlin.test)
        }
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        commonTest.dependencies {
            implementation(compose.uiTest)
            implementation(libs.kotlin.test)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
    }
}

dependencies {
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlin.test)
    debugImplementation(libs.compose.ui.test.manifest)
}
