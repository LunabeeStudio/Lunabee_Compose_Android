/*
 * Copyright © 2022 Lunabee Studio
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
 *
 * settings.gradle.kts
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

rootProject.name = "Lunabee_Kotlin_Library"

include("demo-compose")

// region Compose
include(":lbcaccessibility")
project(":lbcaccessibility").projectDir = File("compose/lbcaccessibility")
include(":lbcandroidtest")
project(":lbcandroidtest").projectDir = File("compose/lbcandroidtest")
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
project(":lbcrobolectrictest").projectDir = File("compose/lbcrobolectrictest")
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

include(":material-color-utilities")

include("lblogger-kermit")
project(":lblogger-kermit").projectDir = File("common/lblogger/lblogger-kermit")
include("lbcore")
project(":lbcore").projectDir = File("common/lbcore/lbcore")
include("lbextensions")
project(":lbextensions").projectDir = File("common/lbextensions")

/*
lbBom = { group = "studio.lunabee", name = "lunabee-bom", version.ref = "lbBom" }
lbTest = { group = "studio.lunabee", name = "lbtest" }
lbktorCore = { group = "studio.lunabee", name = "lbktor-core" }
lbktorJson = { group = "studio.lunabee", name = "lbktor-json" }
lbktorKermit = { group = "studio.lunabee", name = "lbktor-kermit" }
lbloadingCompose = { group = "studio.lunabee", name = "lbloading-compose" }
lbloadingCore = { group = "studio.lunabee", name = "lbloading-core" }
lbloadingKoin = { group = "studio.lunabee", name = "lbloading-koin" }
lbloggerCrashlyticsAndroid = { group = "studio.lunabee", name = "lblogger-kermit-crashlytics-android" }
 */