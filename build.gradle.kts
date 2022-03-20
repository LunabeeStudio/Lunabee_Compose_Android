// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:_")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")
}

detekt {
    parallel = true
    source = files(rootProject.rootDir)
    buildUponDefaultConfig = true
    config = files("$projectDir/lunabee-detekt-config.yml")
    autoCorrect = true
    ignoreFailures = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    outputs.upToDateWhen { false }

    exclude("**/build/**")

    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("$buildDir/reports/detekt/detekt-report.xml"))

        html.required.set(true)
        html.outputLocation.set(file("$buildDir/reports/detekt/detekt-report.html"))
    }
}
