plugins {
    id("lunabee.android-compose-library-conventions")
    id("lunabee.library-publish-conventions")
}

android {
    resourcePrefix("lbc_crop_")
    namespace = "studio.lunabee.compose.crop"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

description = "Helper to perform image croping in compose"
version = AndroidConfig.LBCHAPTIC_VERSION

dependencies {
    implementation(libs.androidxExifinterface)
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.androidxAppcompat)
    implementation(libs.zoomable)
    implementation(libs.coilCompose)
}
