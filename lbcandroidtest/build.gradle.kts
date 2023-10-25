import studio.lunabee.library.setPublication
import studio.lunabee.library.setRepository

plugins {
    id("studio.lunabee.library.android")
}

android {
    resourcePrefix("lbc_at_")
    namespace = "studio.lunabee.compose.androidtest"
}

description = "Tools for developping android test"
version = AndroidConfig.LBC_ANDROID_TEST_VERSION

publishing {
    setRepository(project)
    setPublication(project)
}

signing {
    sign(publishing.publications[project.name])
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.test)
    implementation(libs.compose.ui.test.junit4)
}
