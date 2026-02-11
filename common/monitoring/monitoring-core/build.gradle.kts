plugins {
    id("lunabee.kmp-library-conventions")
    id("lunabee.library-publish-conventions")
}

description = "Monitoring Core library"

kotlin {
    jvm()

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
        }

        commonMain.dependencies {
            api(libs.androidxPagingCommon)
            implementation(libs.kotlinxDatetime)
        }
    }
}
