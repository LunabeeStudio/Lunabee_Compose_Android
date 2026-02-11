plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Ktor remote layer implementation of monitoring"

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
