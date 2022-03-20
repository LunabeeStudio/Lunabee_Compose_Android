plugins {
    `application-flavors`
}

android {

    compileSdk = AndroidConfig.COMPILE_SDK
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK

        versionCode = System.getenv(EnvConfig.ENV_VERSION_CODE)?.toInt() ?: AndroidConfig.VERSION_CODE
        versionName = System.getenv(EnvConfig.ENV_VERSION_NAME) ?: AndroidConfig.VERSION_NAME
    }

    signingConfigs {
        maybeCreate("dev").apply {
        }
        maybeCreate("releaseProd").apply {
        }
    }

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "_"

    buildTypes {
        debug {
            // Uncomment the following line to use the flavor signing
            // signingConfig = null
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles += getDefaultProguardFile("proguard-android-optimize.txt")
            proguardFiles += File("proguard-rules.pro")
        }
    }

    lint {
        abortOnError = false
        checkDependencies = true
        checkGeneratedSources = false
        xmlOutput = file("${project.rootDir}/build/reports/lint/lint-report.xml")
        htmlOutput = file("${project.rootDir}/build/reports/lint/lint-report.html")
        lintConfig = file("${project.rootDir}/lint.xml")
    }
}

dependencies {
    implementation(Kotlin.stdlib.jdk8)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.core.ktx)

    implementation(Google.android.material)

    implementation(Google.accompanist.systemuicontroller)

    debugImplementation(AndroidX.compose.ui.tooling)
    implementation(AndroidX.compose.ui.toolingPreview)
    implementation(AndroidX.activity.compose)
    implementation(AndroidX.compose.foundation)
    implementation(AndroidX.compose.material)
    implementation(AndroidX.navigation.compose)

    implementation(project(Modules.lbctopappbar))
}
