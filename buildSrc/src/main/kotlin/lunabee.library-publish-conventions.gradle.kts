/*
 * Copyright (c) 2024 Lunabee Studio
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
 * lunabee.library-publish-conventions.gradle.kts
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 1/25/2024 - for the Lunabee Compose library.
 */

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet
import org.jreleaser.model.Signing
import studio.lunabee.library.VersionTask
import java.util.Locale

enum class PublishType {
    Android, Java
}

plugins {
    id("org.jreleaser")
    `maven-publish`
    signing
}

val publishType: PublishType = when {
    project.plugins.hasPlugin("android-library") -> PublishType.Android
    project.plugins.hasPlugin("java-library") -> PublishType.Java
    else -> error("Cannot determine the type of publication")
}

private val mavenCentralUsername = project.properties["mavenCentralUsername"]?.toString()
private val mavenCentralPassword = project.properties["mavenCentralPassword"]?.toString()

private val stagingDir = layout.buildDirectory.dir("staging-deploy").get().asFile

jreleaser {
    gitRootSearch.set(true)
    signing {
        active.set(org.jreleaser.model.Active.ALWAYS)
        armored.set(true)
        mode.set(Signing.Mode.FILE)
    }
    deploy {
        maven {
            mavenCentral {
                create("release-deploy") {
                    active.set(org.jreleaser.model.Active.RELEASE)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository(stagingDir.path)
                    username.set(mavenCentralUsername)
                    password.set(mavenCentralPassword)
                    verifyPom.set(false) // FIXME https://github.com/jreleaser/jreleaser.github.io/issues/85
                }
            }
            nexus2 {
                create("snapshot-deploy") {
                    active.set(org.jreleaser.model.Active.SNAPSHOT)
                    url.set("https://central.sonatype.com/repository/maven-snapshots")
                    snapshotUrl.set("https://central.sonatype.com/repository/maven-snapshots")
                    applyMavenCentralRules = true
                    snapshotSupported = true
                    closeRepository = true
                    releaseRepository = true
                    username.set(mavenCentralUsername)
                    password.set(mavenCentralPassword)
                    verifyPom.set(false) // FIXME https://github.com/jreleaser/jreleaser.github.io/issues/85
                    stagingRepository(stagingDir.path)
                }
            }
        }
    }

    release {
        // Disable release feature
        github {
            token.set("fake")
            skipRelease.set(true)
            skipTag.set(true)
        }
    }
}

project.extensions.configure<PublishingExtension>("publishing") {
    setupPublication()

    repositories {
        maven {
            setUrl(stagingDir)
        }
    }
}

/**
 * Entry point for setting publication detail.
 */
fun PublishingExtension.setupPublication() {
    publications {
        when (publishType) {
            PublishType.Android -> create<MavenPublication>(project.name) {
                afterEvaluate { // version is set in project, so use after evaluate
                    setProjectDetails()
                    setAndroidArtifacts()
                    setPom()
                }
            }
            PublishType.Java -> create<MavenPublication>(project.name) {
                afterEvaluate { // version is set in project, so use after evaluate
                    setProjectDetails()
                    setJavaArtifacts()
                    setPom()
                }
            }
        }
    }
}

/**
 * Set project details:
 * - groupId will be [AndroidConfig.GROUP_ID]
 * - artifactId will take the name of the current [project]
 * - version will be set in each submodule gradle file
 */
fun MavenPublication.setProjectDetails() {
    groupId = AndroidConfig.GROUP_ID
    artifactId = project.name
    version = project.version.toString()
}

/**
 * Set POM file details.
 */
fun MavenPublication.setPom() {
    pom {
        name.set(project.name.capitalized())
        description.set(project.description)
        url.set(AndroidConfig.LIBRARY_URL)

        organization {
            name.set("Lunabee Studio")
            url.set("https://www.lunabee.studio")
        }

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        scm {
            connection.set("git@github.com:LunabeeStudio/Lunabee_Compose_Android.git")
            developerConnection.set("git@github.com:LunabeeStudio/Lunabee_Compose_Android.git")
            url.set(AndroidConfig.LIBRARY_URL)
        }

        developers {
            developer {
                id.set("Publisher")
                name.set("Publisher Lunabee")
                email.set("publisher@lunabee.com")
            }
        }

        withXml {
            asNode()
                .appendNode("dependencies").apply {
                    fun Dependency.write(scope: String) = appendNode("dependency").apply {
                        appendNode("groupId", group)
                        appendNode("artifactId", name)
                        version?.let { appendNode("version", version) }
                        appendNode("scope", scope)
                    }

                    configurations.findByName("api")?.dependencies?.forEach { dependency ->
                        dependency.write("implementation")
                    }

                    configurations.findByName("implementation")?.dependencies?.forEach { dependency ->
                        dependency.write("runtime")
                    }
                }
                // For compose-bom which is not on maven central
                .appendNode("repository").apply {
                    appendNode("name", "Google")
                    appendNode("id", "google")
                    appendNode("url", "https://maven.google.com/")
                }
        }
    }
}

private val Project.android: LibraryExtension
    get() = (this as ExtensionAware).extensions.getByName("android") as LibraryExtension

/**
 * Set additional artifacts to upload
 * - sources
 * - javadoc
 * - aar
 */
fun MavenPublication.setAndroidArtifacts() {
    val mainSourceSets = (project.android.sourceSets.getByName("main").kotlin as DefaultAndroidSourceDirectorySet).srcDirs
    val sourceJar by project.tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(mainSourceSets)
    }
    val javadocJar by project.tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
        from(mainSourceSets)
    }

    artifact(sourceJar)
    artifact(javadocJar)
    val aarBasePath = project.layout.buildDirectory.dir("outputs/aar").get().asFile.path
    val filename = "${project.name.lowercase()}-release.aar"
    artifact("$aarBasePath/$filename")

    project.afterEvaluate {
        project.tasks.named("sign${project.name.capitalized()}Publication") {
            dependsOn("bundleReleaseAar")
        }
    }
}

/**
 * Set additional artifacts to upload
 * - sources
 * - javadoc
 * - jar
 */
fun MavenPublication.setJavaArtifacts() {
    artifact(project.layout.buildDirectory.dir("libs/${project.name}-${project.version}.jar").get().asFile)
    artifact(project.tasks.named("sourcesJar"))
    artifact(project.tasks.named("javadocJar"))

    project.afterEvaluate {
        project.tasks.named("sign${project.name.capitalized()}Publication") {
            dependsOn("jar")
        }
    }
}

signing {
    setRequired {
        {
            gradle.taskGraph.hasTask("publish")
        }
    }
    sign(publishing.publications[project.name])
}

afterEvaluate {
    tasks.withType(PublishToMavenRepository::class.java) {
        when (publishType) {
            PublishType.Android -> dependsOn(
                tasks.named("bundleReleaseAar"),
            )
            PublishType.Java -> dependsOn(
                tasks.withType(Jar::class.java),
            )
        }
    }
}

tasks.register("${project.name}Version", VersionTask::class.java)

private fun String.capitalized(): String = if (this.isEmpty()) this else this[0].titlecase(Locale.getDefault()) + this.substring(1)
