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

import com.android.build.gradle.LibraryExtension
import org.jreleaser.model.Signing
import studio.lunabee.library.VersionTask
import java.util.Locale

enum class PublishType {
    Android,
    Java,
    Kmp,
    Bom,
}

plugins {
    id("org.jreleaser")
    `maven-publish`
    signing
    id("org.jetbrains.dokka")
    id("org.jetbrains.dokka-javadoc")
}

private val publishType: PublishType = when {
    project.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform") -> PublishType.Kmp
    project.plugins.hasPlugin("android-library") -> PublishType.Android
    project.plugins.hasPlugin("java-library") -> PublishType.Java
    project.plugins.hasPlugin("java-platform") -> PublishType.Bom
    else -> error("Cannot determine the type of publication")
}

private val mavenCentralUsername = project.properties["mavenCentralUsername"]?.toString()
private val mavenCentralPassword = project.properties["mavenCentralPassword"]?.toString()

private val stagingDir = layout.buildDirectory
    .dir("staging-deploy")
    .get()
    .asFile

jreleaser {
    gitRootSearch.set(true)
    signing {
        active.set(org.jreleaser.model.Active.ALWAYS)
        pgp {
            armored.set(true)
            mode.set(Signing.Mode.FILE)
        }
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

publishing {
    repositories {
        maven {
            setUrl(stagingDir)
        }
    }

    setupPublication()
}

/**
 * Entry point for setting publication detail.
 */
fun PublishingExtension.setupPublication() {
    publications {
        when (publishType) {
            PublishType.Android -> create<MavenPublication>(project.name) {
                // version is set in project, so use after evaluate
                afterEvaluate {
                    setProjectDetails()
                    from(components["release"])
                    setPom()
                }
                val (dokkaJavadocJar, dokkaHtmlJar) = setupDokkaTasks()
                artifact(dokkaJavadocJar)
                artifact(dokkaHtmlJar)
            }
            PublishType.Java -> create<MavenPublication>(project.name) {
                // version is set in project, so use after evaluate
                afterEvaluate {
                    setProjectDetails()
                    setPom()
                }
                val (dokkaJavadocJar, dokkaHtmlJar) = setupDokkaTasks()
                from(components["java"])
                artifact(dokkaJavadocJar)
                artifact(dokkaHtmlJar)
            }
            PublishType.Kmp -> publications.withType<MavenPublication> {
                setPom()
            }
            PublishType.Bom -> create<MavenPublication>(project.name) {
                afterEvaluate {
                    setProjectDetails()
                    from(components["javaPlatform"])
                    setPom()
                }
            }
        }
    }
}

/**
 * Setup Maven publication from project details
 */
private fun MavenPublication.setProjectDetails() {
    this.groupId = project.group.toString()
    this.artifactId = if (publishType == PublishType.Bom) "lunabee-bom" else project.name
    this.version = project.version.toString()
    logger.log(LogLevel.INFO, "Set publication details: groupId=$groupId, artifactId=$artifactId, version=$version")
}

/**
 * Setup Dokka manually to workaround https://github.com/Kotlin/dokka/issues/2956
 */
private fun setupDokkaTasks(): Pair<TaskProvider<Jar>, TaskProvider<Jar>> {
    val dokkaJavadocJar by tasks.registering(Jar::class) {
        description = "A Javadoc JAR containing Dokka Javadoc"
        from(tasks.dokkaGeneratePublicationJavadoc.flatMap { it.outputDirectory })
        archiveClassifier.set("javadoc")
    }

    val dokkaHtmlJar by tasks.registering(Jar::class) {
        description = "A HTML Documentation JAR containing Dokka HTML"
        from(tasks.dokkaGeneratePublicationHtml.flatMap { it.outputDirectory })
        archiveClassifier.set("html-doc")
    }

    return dokkaJavadocJar to dokkaHtmlJar
}

/**
 * Set POM file details.
 */
private fun MavenPublication.setPom() {
    pom {
        name.set(project.name.capitalized())
        description.set(project.description)
        url.set(AndroidConfig.LibraryUrl)

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
            connection.set("git@github.com:LunabeeStudio/LBAndroid.git")
            developerConnection.set("git@github.com:LunabeeStudio/LBAndroid.git")
            url.set(AndroidConfig.LibraryUrl)
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

private val Project.android: LibraryExtension
    get() = (this as ExtensionAware).extensions.getByName("android") as LibraryExtension

signing {
    setRequired {
        {
            gradle.taskGraph.hasTask("publish")
        }
    }
    publishing.publications.forEach {
        sign(it)
    }
}

afterEvaluate {
    tasks.withType(PublishToMavenRepository::class.java) {
        when (publishType) {
            PublishType.Android -> dependsOn(
                tasks.named("bundleReleaseAar"),
            )
            PublishType.Kmp,
            PublishType.Java,
            PublishType.Bom,
            -> dependsOn(
                tasks.withType(Jar::class.java),
            )
        }
    }
}

tasks.register("${project.name}Version", VersionTask::class.java)

private fun String.capitalized(): String = if (this.isEmpty()) this else this[0].titlecase(Locale.getDefault()) + this.substring(1)

private fun Dependency.isBoM(): Boolean = name.endsWith("-bom")
