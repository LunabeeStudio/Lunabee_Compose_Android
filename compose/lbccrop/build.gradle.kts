plugins {
    id("lunabee.android-library-conventions")
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
    implementation(libs.androidx.exifinterface)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.androidx.appcompat)
    implementation(libs.zoomable)
    implementation(libs.coil.compose)
}
