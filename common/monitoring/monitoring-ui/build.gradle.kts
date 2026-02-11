plugins {
    id("lunabee.kmp-android-library-conventions")
    id("lunabee.library-publish-conventions")
    id(libs.plugins.composePlugin.get().pluginId)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinxSerialization)
}

description = "Compose UI for monitoring"
version = AndroidConfig.MONITORING_UI_VERSION

kotlin {

    androidLibrary {
        namespace = "studio.lunabee.monitoring.ui"
    }

    sourceSets {
        all {
            languageSettings.optIn("androidx.compose.foundation.ExperimentalFoundationApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
        }
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koinBom))

            implementation(libs.androidxPagingCommon)
            implementation(libs.composeNavigation)
            implementation(libs.jetbrainsComponentsResources)
            implementation(libs.jetbrainsFoundation)
            implementation(libs.jetbrainsMaterial3)
            implementation(libs.jetbrainsRuntime)
            implementation(libs.jetbrainsUi)
            implementation(libs.koinCompose)
            implementation(libs.koinCore)
            implementation(libs.koinViewmodel)
            implementation(libs.kotlinxViewModel)
            implementation(libs.kotlinxDatetime)
            implementation(libs.kotlinxSerializationJson)

            implementation(projects.monitoringCore)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "studio.lunabee.monitoring.ui.res"
    generateResClass = auto

    customDirectory(
        sourceSetName = "iosMain",
        directoryProvider = provider { layout.projectDirectory.dir("src/iosMain/iosResources") },
    )

    customDirectory(
        sourceSetName = "androidMain",
        directoryProvider = provider { layout.projectDirectory.dir("src/androidMain/androidResources") },
    )
}
