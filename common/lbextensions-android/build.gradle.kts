plugins {
    id("lunabee.android-library-conventions")
    id("lunabee.library-publish-conventions")
}

android {
    namespace = "com.lunabee.lbextensions"
    resourcePrefix("lb_e_")
}

description = "Lunabee Studio extensions lib"
version = AndroidConfig.LBEXTENSIONS_ANDROID_VERSION

dependencies {
    // AndroidX
    implementation(libs.androidxAppcompat)
    implementation(libs.androidxCore)
    implementation(libs.androidxExifinterface)
    implementation(libs.androidxLifecycleRuntime)
    implementation(libs.androidxRecyclerview)
    implementation(libs.googleAndroidMaterial)

    implementation(projects.lbextensions)
    // Lunabee
    implementation(projects.lbloggerKermit)

    androidTestImplementation(libs.androidxPreferenceKtx)
    androidTestImplementation(libs.androidxTestCoreKtx)
    androidTestImplementation((libs.junit4))
    androidTestImplementation(libs.kotlinTest)
}
