package studio.lunabee.library

import BuildConfigs
import Kotlin
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.configureAndroidPlugins(): Unit = plugins.run {
    apply("com.android.library")
    apply("kotlin-android")
}

fun Project.configureAndroid(): Unit = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(BuildConfigs.compileSdk)

    defaultConfig {
        minSdk = BuildConfigs.minSdk
        targetSdk = BuildConfigs.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    this.buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "_"
}

fun Project.configureDependencies(): Unit = dependencies {
    add("implementation", Kotlin.Stdlib.jdk8)
}
