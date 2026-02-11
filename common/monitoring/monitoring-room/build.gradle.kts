plugins {
    id("lunabee.kmp-android-library-conventions")
    id("lunabee.library-publish-conventions")
    alias(libs.plugins.androidxRoom)
    alias(libs.plugins.ksp)
}

description = "Room persistence layer implementation for monitoring"

kotlin {
    androidLibrary {
        namespace = "studio.lunabee.monitoring.room"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
        }

        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.koinBom))

            implementation(libs.koinAndroid)
        }

        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koinBom))

            implementation(libs.androidxRoomPaging)
            implementation(libs.androidxRoomRuntime)
            implementation(libs.androidxSqliteBundled)
            implementation(libs.koinCore)
            implementation(libs.kotlinxDatetime)

            implementation(projects.monitoringCore)
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidxRoomCompiler)
    add("kspIosArm64", libs.androidxRoomCompiler)
    add("kspIosSimulatorArm64", libs.androidxRoomCompiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}
