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
    id(libs.plugins.compose.plugin.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "studio.lunabee.compose"

    compileSdk = AndroidConfig.CompileSdk

    defaultConfig {
        minSdk = AndroidConfig.MinSdk
        targetSdk = AndroidConfig.TargetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        versionCode = System.getenv(EnvConfig.EnvVersionCode)?.toInt() ?: AndroidConfig.VersionCode
        versionName = System.getenv(EnvConfig.EnvVersionName) ?: AndroidConfig.VersionName
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.JDK_VERSION
        targetCompatibility = AndroidConfig.JDK_VERSION
    }

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

    hilt {
        enableAggregatingTask = true
    }

    lint {
        abortOnError = false
        checkDependencies = true
        checkGeneratedSources = false
        xmlOutput = file("${project.rootDir}/build/reports/lint/lint-report.xml")
        htmlOutput = file("${project.rootDir}/build/reports/lint/lint-report.html")
        lintConfig = file("${project.rootDir}/lint.xml")
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

dependencies {
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidxUiToolingPreview)

    coreLibraryDesugaring(libs.desugarJdk)

    implementation(project(Modules.LbcAccessibility))
    implementation(project(Modules.LbcFoundation))
    implementation(project(Modules.LbcTheme))
    implementation(project(Modules.LbcCore))
    implementation(project(Modules.LbcHaptic))
    implementation(project(Modules.LbcCrop))
    implementation(project(Modules.LbcUiFieldCore))
    implementation(project(Modules.LbcUiFieldPhonePicker))
    implementation(project(Modules.LbcUiFieldCountryPicker))
    implementation(project(Modules.LbcPresenter))
    implementation(project(Modules.LbcPresenterKoin))
    implementation(project(Modules.LbcGlance))
    implementation(project(Modules.LbcImage))

    androidTestImplementation(project(Modules.LbcAndroidTest))
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlin.test.junit)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    //noinspection UseTomlInstead FIXME wait for dagger update https://github.com/google/dagger/issues/5001
    ksp("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.0")
    implementation(libs.androidx.hilt.navigation.compose)

    debugImplementation(libs.androidxUiTooling)
}

kotlin.compilerOptions.jvmTarget.set(AndroidConfig.JVM_TARGET)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AndroidConfig.JDK_VERSION.toString()))
    }
}
