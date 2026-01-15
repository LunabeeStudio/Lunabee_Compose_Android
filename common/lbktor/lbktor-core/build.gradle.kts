plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Provides utils methods to get and configure an HttpClient with Ktor"
version = AndroidConfig.LBKTOR_VERSION

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.ktorClientCore)
            implementation(libs.ktorLogging)
        }
    }
}
