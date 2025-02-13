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
 * VersionTask.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 5/9/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.library

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class DevelopTask : DefaultTask() {
    @TaskAction
    fun setSnapshotVersion() {
        val file = File(project.rootDir.path + "/buildSrc/src/main/kotlin/AndroidConfig.kt")
        val newContents = file
            .readText()
            .replace(
                regex = libVersion(),
            ) { matchResult ->
                "${matchResult.value}-rc-${project.properties["counter"]}"
            }
        file.writeText(newContents)

        println(newContents)
    }
}
