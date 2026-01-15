plugins {
    id("lunabee.android-compose-library-conventions")
    id("lunabee.library-publish-conventions")
}

android {
    namespace = "com.lunabee.lbcorecompose"
    resourcePrefix("lb_")
}

description = "Lunabee Studio core compose lib"
version = AndroidConfig.LBCORE_VERSION

dependencies {

    // Lunabee
    api(projects.lbcoreAndroid)

    // Compose
    implementation(platform(libs.composeBom))
    implementation(libs.composeRuntime)
    implementation(libs.composeUiUnit)

    debugImplementation(libs.androidxUiTestManifest)
    androidTestImplementation(libs.androidxUiTest)
    androidTestImplementation(libs.androidxUiTestJunit4)
    androidTestImplementation(libs.kotlinTest)
}
