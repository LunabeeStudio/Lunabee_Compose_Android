/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        mavenLocal()
    }
}

rootProject.name = "LBAndroid"

include("demo-compose")

// region Compose
include(":compose:material-color-utilities")
project(":compose:material-color-utilities").projectDir = File("compose/material-color-utilities")
include(":compose:accessibility")
project(":compose:accessibility").projectDir = File("compose/accessibility")
include(":compose:androidtest")
project(":compose:androidtest").projectDir = File("compose/test/androidtest")
include(":compose:core")
project(":compose:core").projectDir = File("compose/core")
include(":compose:crop")
project(":compose:crop").projectDir = File("compose/crop")
include(":compose:foundation")
project(":compose:foundation").projectDir = File("compose/foundation")
include(":compose:glance")
project(":compose:glance").projectDir = File("compose/glance")
include(":compose:haptic")
project(":compose:haptic").projectDir = File("compose/haptic")
include(":compose:image")
project(":compose:image").projectDir = File("compose/image")
include(":compose:theme")
project(":compose:theme").projectDir = File("compose/theme")
include(":compose:robolectrictest")
project(":compose:robolectrictest").projectDir = File("compose/test/robolectrictest")
include(":compose:presenter")
project(":compose:presenter").projectDir = File("compose/presenter/presenter")
include(":compose:presenter-koin")
project(":compose:presenter-koin").projectDir = File("compose/presenter/presenter-koin")
include(":compose:uifield-core")
project(":compose:uifield-core").projectDir = File("compose/uifield/uifield-core")
include(":compose:uifield-countrypicker")
project(":compose:uifield-countrypicker").projectDir = File("compose/uifield/uifield-countrypicker")
include(":compose:uifield-phonepicker")
project(":compose:uifield-phonepicker").projectDir = File("compose/uifield/uifield-phonepicker")
// endregion

// region Common
include("platform")
project(":platform").projectDir = File("common/platform")
include("logger-kermit")
project(":logger-kermit").projectDir = File("common/logger/logger-kermit")
include("logger-kermit-crashlytics")
project(":logger-kermit-crashlytics").projectDir = File("common/logger/logger-kermit-crashlytics")
include("core")
project(":core").projectDir = File("common/core/core")
include("core-compose")
project(":core-compose").projectDir = File("common/core/core-compose")
include("core-android")
project(":core-android").projectDir = File("common/core/core-android")
include("extension")
project(":extension").projectDir = File("common/extension/extension")
include("extension-android")
project(":extension-android").projectDir = File("common/extension/extension-android")
include("test")
project(":test").projectDir = File("common/test")
include(":ktor-core")
project(":ktor-core").projectDir = File("common/ktor/ktor-core")
include(":ktor-kermit")
project(":ktor-kermit").projectDir = File("common/ktor/ktor-kermit")
include(":ktor-json")
project(":ktor-json").projectDir = File("common/ktor/ktor-json")
include(":loading-core")
project(":loading-core").projectDir = File("common/loading/loading-core")
include(":loading-checks")
project(":loading-checks").projectDir = File("common/loading/loading-compose/checks")
include(":loading-compose")
project(":loading-compose").projectDir = File("common/loading/loading-compose")
include(":loading-hilt")
project(":loading-hilt").projectDir = File("common/loading/loading-hilt")
include(":loading-koin")
project(":loading-koin").projectDir = File("common/loading/loading-koin")
// endregion
