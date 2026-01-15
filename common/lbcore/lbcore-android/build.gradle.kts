plugins {
    id("lunabee.android-library-conventions")
    id("lunabee.library-publish-conventions")
}

android {
    namespace = "com.lunabee.lbcoreandroid"
    resourcePrefix("lb_")
}

description = "Lunabee Studio core lib"
version = AndroidConfig.LBCORE_VERSION

dependencies {

    // Lunabee
    api(projects.lbcore)
    implementation(projects.lbloggerKermit)

    // Kotlin
    implementation(libs.kotlinxCoroutinesAndroid)

    // AndroidX
    implementation(libs.androidxLifecycleCommon)
    implementation(libs.androidxLifecycleProcess)
    implementation(libs.androidxLifecycleRuntime)
    implementation(libs.androidxCore)
    implementation(libs.androidxFragmentKtx)
    implementation(libs.googleAndroidMaterial)
}
