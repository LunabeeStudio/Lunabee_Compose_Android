@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    alias(libs.plugins.compose)
    id(libs.plugins.compose.plugin.get().pluginId)
}

group = "studio.lunabee.compose"
version = "1.0"

compose.resources {
    packageOfResClass = "studio.lunabee.compose.webapp.generated.resources"
    generateResClass = always
}

kotlin {
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(project(Modules.LbcCoreKmp))
        }
    }
}
