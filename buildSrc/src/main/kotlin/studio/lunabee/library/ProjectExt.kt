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
 * ProjectExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.library

import BuildConfigs
import Kotlin
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

val excludedTestProjects: List<String> = listOf(
    "lbcandroidtest",
)

fun Project.configureAndroidPlugins(): Unit = plugins.run {
    apply("com.android.library")
    apply("kotlin-android")
    apply("maven-publish") // publish to Sonatype.
    apply("signing") // Sign publish with a GPG key.
}

fun Project.configureJavaPlugins(): Unit = plugins.run {
    apply("java-library")
    apply("org.jetbrains.kotlin.jvm")
    apply("maven-publish") // publish to Sonatype.
    apply("signing") // Sign publish with a GPG key.
}

fun Project.configureAndroid(): Unit = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(BuildConfigs.compileSdk)

    defaultConfig {
        minSdk = BuildConfigs.minSdk
        targetSdk = BuildConfigs.targetSdk

        if (!excludedTestProjects.contains(name)) {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    this.buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "_"
}

fun Project.configureDependencies(): Unit = dependencies {
    add("implementation", Kotlin.Stdlib.jdk8)
    if (!excludedTestProjects.contains(name)) {
        add("androidTestImplementation", AndroidX.test.runner)
    }
}
