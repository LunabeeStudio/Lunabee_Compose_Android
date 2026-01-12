plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Lunabee Studio Kotlin core library"
version = AndroidConfig.LBCORE_VERSION

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.kotlinxCoroutinesBom))
            implementation(libs.kotlinxCoroutinesCore)
            api(projects.lbloggerKermit)
        }
        jvmTest.dependencies {
            implementation(libs.kotlinTest)
            implementation(project.dependencies.platform(libs.junitJupiterBom))
            implementation(libs.junitJupiter)
            implementation(libs.kotlinTest)
            implementation(libs.kotlinxCoroutinesTest)
        }
        nativeMain {
        }
    }
}
