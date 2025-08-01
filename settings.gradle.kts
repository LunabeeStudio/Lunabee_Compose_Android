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

rootProject.name = "Lunabee Compose"

include("app")
include(":lbcaccessibility")
include(":lbcandroidtest")
include(":lbccore")
include(":lbccrop")
include(":lbcfoundation")
include(":lbcglance")
include(":lbchaptic")
include(":lbcimage")
include(":lbctheme")
include(":material-color-utilities")

include(":lbcpresenter")
project(":lbcpresenter").projectDir = File("lbcpresenter/lbcpresenter")
include(":lbcpresenter-koin")
project(":lbcpresenter-koin").projectDir = File("lbcpresenter/lbcpresenter-koin")

include(":lbcuifield-core")
project(":lbcuifield-core").projectDir = File("lbcuifield/lbcuifield-core")
include(":lbcuifield-countrypicker")
project(":lbcuifield-countrypicker").projectDir = File("lbcuifield/lbcuifield-countrypicker")
include(":lbcuifield-phonepicker")
project(":lbcuifield-phonepicker").projectDir = File("lbcuifield/lbcuifield-phonepicker")
