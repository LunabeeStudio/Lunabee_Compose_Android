plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Lunabee Studio Kotlin extensions library"
version = AndroidConfig.LBEXTENSIONS_VERSION

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.kotlinxCoroutinesBom))
            implementation(libs.kotlinxCoroutinesCore)
            implementation(libs.kotlinxDatetime)

            api(projects.lbloggerKermit)
        }
        commonTest.dependencies {
            implementation(libs.kotlinTest)
            implementation(libs.kotlinxCoroutinesTest)
        }
    }
}
