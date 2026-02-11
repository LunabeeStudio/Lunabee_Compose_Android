plugins {
    id("lunabee.android-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "OkHttp integration to monitoring library"
version = AndroidConfig.MONITORING_OKHTTP_VERSION

android {
    namespace = "studio.lunabee.onitoring.okhttp"
}

dependencies {
    implementation(libs.kotlinxDatetime)
    implementation(libs.kotlinxSerializationJson)
    implementation(libs.okhttp)

    implementation(projects.monitoringCore)
    implementation(projects.loggerKermit)
}
