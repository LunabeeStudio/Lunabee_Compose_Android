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
include(":material-color-utilities")
project(":material-color-utilities").projectDir = File("compose/material-color-utilities")
include(":lbcaccessibility")
project(":lbcaccessibility").projectDir = File("compose/lbcaccessibility")
include(":lbcandroidtest")
project(":lbcandroidtest").projectDir = File("compose/lbctest/lbcandroidtest")
include(":lbccore")
project(":lbccore").projectDir = File("compose/lbccore")
include(":lbccrop")
project(":lbccrop").projectDir = File("compose/lbccrop")
include(":lbcfoundation")
project(":lbcfoundation").projectDir = File("compose/lbcfoundation")
include(":lbcglance")
project(":lbcglance").projectDir = File("compose/lbcglance")
include(":lbchaptic")
project(":lbchaptic").projectDir = File("compose/lbchaptic")
include(":lbcimage")
project(":lbcimage").projectDir = File("compose/lbcimage")
include(":lbctheme")
project(":lbctheme").projectDir = File("compose/lbctheme")
include(":lbcrobolectrictest")
project(":lbcrobolectrictest").projectDir = File("compose/lbctest/lbcrobolectrictest")
include(":lbcpresenter")
project(":lbcpresenter").projectDir = File("compose/lbcpresenter/lbcpresenter")
include(":lbcpresenter-koin")
project(":lbcpresenter-koin").projectDir = File("compose/lbcpresenter/lbcpresenter-koin")
include(":lbcuifield-core")
project(":lbcuifield-core").projectDir = File("compose/lbcuifield/lbcuifield-core")
include(":lbcuifield-countrypicker")
project(":lbcuifield-countrypicker").projectDir = File("compose/lbcuifield/lbcuifield-countrypicker")
include(":lbcuifield-phonepicker")
project(":lbcuifield-phonepicker").projectDir = File("compose/lbcuifield/lbcuifield-phonepicker")
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
