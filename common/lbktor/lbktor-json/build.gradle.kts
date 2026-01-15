plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Provides Json ContentNegotiation for Ktor with Kotlinx Json Serialization"
version = AndroidConfig.LBKTOR_VERSION

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktorClientCore)
            implementation(libs.ktorContentNegotiation)
            api(libs.ktorJsonSerialization)
            implementation(project(":lbktor-core"))
        }
    }
}
