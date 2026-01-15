plugins {
    id("com.android.library")
    id("lunabee.kmp-library-conventions")
}

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
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
    }
}
