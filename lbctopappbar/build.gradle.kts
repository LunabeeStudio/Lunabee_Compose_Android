plugins {
    id("studio.lunabee.library.android")
}

android {
    resourcePrefix("lbc_tab_")
}

description = "Lunabee Studio Compose library related to TopBar"

dependencies {
    implementation(AndroidX.compose.material)
    implementation(AndroidX.constraintLayout.compose)
    implementation(AndroidX.compose.foundation)

    // Needed for preview.
    implementation(AndroidX.appCompat)
    implementation(AndroidX.compose.ui.toolingPreview)
    debugImplementation(AndroidX.compose.ui.tooling)
}
