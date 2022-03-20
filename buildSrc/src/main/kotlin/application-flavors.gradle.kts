plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
        }

        create("prod") {
            dimension = "environment"
        }
    }
}
