plugins {
    id("lunabee.kmp-android-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Lunabee Studio Kotlin crashlytics for Kermit logger"
version = AndroidConfig.LBLOGGER_KERMIT_VERSION

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.touchlabKermit)
            implementation(libs.touchlabKermitCrashlytics)
        }
    }
}

android {
    namespace = "com.lunabee.lblogger.kermit"
    resourcePrefix("lb_log")
}
