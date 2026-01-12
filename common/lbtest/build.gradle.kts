plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Lunabee Studio Kotlin test library"
version = AndroidConfig.LBTEST_VERSION

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinTest)
        }
        jvmTest.dependencies {
        }
        nativeMain {
        }
    }
}
