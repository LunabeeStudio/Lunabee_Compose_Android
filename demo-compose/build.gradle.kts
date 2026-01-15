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

plugins {
    id("com.android.application")
    id("kotlin-android")
    id(libs.plugins.composePlugin.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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
    coreLibraryDesugaring(libs.desugarJdk)

    implementation(platform(libs.composeBom))

    ksp(libs.hiltAndroidCompiler)
    //noinspection UseTomlInstead FIXME wait for dagger update https://github.com/google/dagger/issues/5001
    ksp("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.0")

    implementation(libs.androidxActivityCompose)
    implementation(libs.androidxAppcompat)
    implementation(libs.androidxCore)
    implementation(libs.androidxHiltNavigationCompose)
    implementation(libs.androidxLifecycleRuntimeCompose)
    implementation(libs.androidxUiToolingPreview)
    implementation(libs.composeFoundation)
    implementation(libs.composeMaterial3)
    implementation(libs.composeMaterialIconsExtended)
    implementation(libs.googleAndroidMaterial)
    implementation(libs.hiltAndroid)
    implementation(libs.navigationCompose)

    implementation(projects.lbcaccessibility)
    implementation(projects.lbccore)
    implementation(projects.lbccrop)
    implementation(projects.lbcfoundation)
    implementation(projects.lbcglance)
    implementation(projects.lbchaptic)
    implementation(projects.lbcimage)
    implementation(projects.lbcpresenter)
    implementation(projects.lbcpresenterKoin)
    implementation(projects.lbctheme)
    implementation(projects.lbcuifieldCore)
    implementation(projects.lbcuifieldCountrypicker)
    implementation(projects.lbcuifieldPhonepicker)

    debugImplementation(libs.androidxUiTooling)
    debugImplementation(libs.composeUiTestManifest)

    androidTestImplementation(libs.composeUiTestJunit4)
    androidTestImplementation(libs.junit4)
    androidTestImplementation(libs.kotlinTestJunit)
    androidTestImplementation(projects.lbcandroidtest)
}

kotlin.compilerOptions.jvmTarget.set(AndroidConfig.JVM_TARGET)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AndroidConfig.JDK_VERSION.toString()))
    }
}
