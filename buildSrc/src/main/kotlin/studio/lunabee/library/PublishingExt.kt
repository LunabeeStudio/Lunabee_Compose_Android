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
 * PublishingExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/20/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.library

import AndroidConfig
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import java.net.URI
import java.util.Locale

private val Project.android: LibraryExtension
    get() = (this as ExtensionAware).extensions.getByName("android") as LibraryExtension
/**
 * Set repository destination depending on [project] and version name.
 * Credentials should be stored in your root gradle.properties, in a non source controlled file.
 *
 * @param project current project
 */
fun PublishingExtension.setRepository(project: Project) {
    repositories {
        maven {
            authentication {
                credentials.username = project.properties["sonatypeUsername"]?.toString() ?: System.getenv("SONATYPE_USERNAME")
                credentials.password = project.properties["sonatypePassword"]?.toString() ?: System.getenv("SONATYPE_PASSWORD")
            }
            url = if (project.version.toString().endsWith("-SNAPSHOT")) {
                URI.create("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            } else {
                URI.create("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }
    }
}

/**
 * Entry point for setting publication detail.
 *
 * @param project current project
 */
fun PublishingExtension.setPublication(project: Project) {
    publications { setPublication(project) }
}

private fun PublicationContainer.setPublication(project: Project) {
    this.create<MavenPublication>(project.name) {
        setProjectDetails(project)
        setArtifacts(project)
        setPom(project)
    }
}

/**
 * Set project details:
 * - groupId will be [AndroidConfig.GROUP_ID]
 * - artifactId will take the name of the current [project]
 * - version will be set in each submodule gradle file
 *
 * @param project project current project
 */
private fun MavenPublication.setProjectDetails(project: Project) {
    groupId = AndroidConfig.GROUP_ID
    artifactId = project.name
    version = project.version.toString()
}

/**
 * Set additional artifacts to upload on Sonatype:
 * - sources
 * - javadoc
 * - and final aar
 *
 * @param project project current project
 */
private fun MavenPublication.setArtifacts(project: Project) {
    val sourceJar by project.tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(project.android.sourceSets.getByName("main").java.srcDirs)
    }

    val javadocJar by project.tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
        from(project.android.sourceSets.getByName("main").java.srcDirs)
    }

    artifact(sourceJar)
    artifact(javadocJar)
    artifact("${project.buildDir}/outputs/aar/${project.name.toLowerCase(Locale.getDefault())}-release.aar")
}

/**
 * Set POM file details.
 *
 * @param project project current project
 */
private fun MavenPublication.setPom(project: Project) {
    pom {
        name.set(project.name)
        description.set(project.description)
        url.set(AndroidConfig.LIBRARY_URL)

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        scm {
            connection.set("git@github.com:LunabeeStudio/Lunabee_Compose_Android.git")
            developerConnection.set("git@github.com:LunabeeStudio/Lunabee_Compose_Android.git")
            url.set("https://github.com/LunabeeStudio/Lunabee_Compose_Android")
        }

        developers {
            developer {
                id.set("Publisher")
                name.set("Publisher Lunabee")
                email.set("publisher@lunabee.com")
            }
        }
    }
}
