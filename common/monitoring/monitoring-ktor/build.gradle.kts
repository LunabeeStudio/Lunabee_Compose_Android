plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Ktor remote layer implementation of monitoring"
version = AndroidConfig.MONITORING_KTOR_VERSION

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinxDatetime)
            implementation(libs.kotlinxSerializationJson)
            implementation(libs.ktorClientCore)

            implementation(projects.monitoringCore)
            implementation(projects.ktorCore)
        }
    }
}
