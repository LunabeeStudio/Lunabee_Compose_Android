plugins {
    id("java-library")
    id("kotlin")
}

description = "Lint rules for gloabl loading library"
version = AndroidConfig.LBLOADING_VERSION

dependencies {
    compileOnly(libs.lintApi)
    compileOnly(libs.lintChecks)
}

java {
    sourceCompatibility = AndroidConfig.JDK_VERSION
    targetCompatibility = AndroidConfig.JDK_VERSION
}
