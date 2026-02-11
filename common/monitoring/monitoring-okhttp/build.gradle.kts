plugins {
    id("lunabee.android-library-conventions")
    id("lunabee.library-publish-conventions")
}

android {
    namespace = "studio.lunabee.onitoring.okhttp"
}

description = "OkHttp integration to monitoring library"

dependencies {
    implementation(libs.kotlinxDatetime)
    implementation(libs.kotlinxSerializationJson)
    implementation(libs.okhttp)

    implementation(projects.monitoringCore)
    implementation(projects.loggerKermit)
}
