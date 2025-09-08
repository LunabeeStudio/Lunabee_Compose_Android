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
 * build.gradle.kts
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/21/2022 - for the Lunabee Compose library.
 */

plugins {
    id("lunabee.java-library-conventions")
    id("lunabee.library-publish-conventions")
}

version = AndroidConfig.MATERIAL_COLOR_UTILITIES_VERSION
description =
    "Algorithms and utilities that power the Material Design 3 (M3) color system, including choosing theme colors from images" +
    " and creating tones of colors; all in a new color space.\n" +
    "See https://github.com/material-foundation/material-color-utilities"
