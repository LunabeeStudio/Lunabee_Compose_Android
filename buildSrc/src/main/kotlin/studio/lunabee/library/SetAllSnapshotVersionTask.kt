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
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

abstract class SetAllSnapshotVersionTask : DefaultTask() {

    @get:Option(option = "buildNumber", description = "Build number to append to the snapshot version")
    @get:Input
    abstract val buildNumber: Property<Int>

    @get:Inject abstract val eo: ExecOperations

    @TaskAction
    fun setSnapshotVersion() {
        val projectsVersionVal = project.allprojects
            .filter { it.plugins.hasPlugin("maven-publish") }
            .map {
                it.name
                    .replace("-", "_")
                    .uppercase()
                    .plus("_VERSION")
            }
        println(projectsVersionVal.joinToString("\n"))

        val androidConfig = File(project.rootDir.path + "/buildSrc/src/main/kotlin/AndroidConfig.kt")
        val branch = getGitBranch()
        var newContents = androidConfig.readText()
        projectsVersionVal.forEach { projectVersionVal ->
            newContents = newContents
                .replace(
                    regex = Regex("$projectVersionVal: String = \"[^\"]*"),
                ) { matchResult ->
                    "${matchResult.value}-alpha-${buildNumber.get()}-$branch-SNAPSHOT"
                }
        }
        androidConfig.writeText(newContents)

        println(newContents)
    }

    private fun getGitBranch(): String {
        val output = ByteArrayOutputStream()
        eo.exec {
            commandLine = "git rev-parse --abbrev-ref HEAD".split(" ")
            standardOutput = output
        }
        return output.toString()
            .split('/')
            .last()
            .trim()
    }
}
