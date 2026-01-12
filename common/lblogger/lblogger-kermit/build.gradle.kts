plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Lunabee Studio Kotlin logger"
version = AndroidConfig.LBLOGGER_KERMIT_VERSION

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.touchlabKermit)
        }
        jvmTest.dependencies {
            implementation(project.dependencies.platform(libs.junitJupiterBom))
            implementation(libs.junitJupiter)
            implementation(libs.junitJupiterPlatformLauncher)
        }
    }
}
