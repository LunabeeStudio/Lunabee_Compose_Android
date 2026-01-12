plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Lunabee Studio Kotlin test library"

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.test)
        }
        jvmTest.dependencies {
        }
        nativeMain {
        }
    }
}
