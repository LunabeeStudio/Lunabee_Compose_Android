plugins {
    `java-platform`
    id("lunabee.library-publish-conventions")
}

val rootProject: String = "studio.lunabee"
val versionName: String = AndroidConfig.commonVersionName()

dependencies {
    constraints {
        addKmpModule(projects.lbcore.name, Target.Jvm)
        addModule(projects.lbcoreAndroid.name)
        addModule(projects.lbcoreCompose.name)
        addModule(projects.lbextensions.name)
        addKmpModule(projects.lbktorCore.name, Target.Jvm)
        addKmpModule(projects.lbktorJson.name, Target.Jvm)
        addKmpModule(projects.lbktorKermit.name, Target.Jvm)
        addModule(projects.lbloadingCompose.name)
        addModule(projects.lbloadingChecks.name)
        addKmpModule(projects.lbloadingCore.name, Target.Jvm)
        addModule(projects.lbloadingHilt.name)
        addModule(projects.lbloadingKoin.name)
        addKmpModule(projects.lbloggerKermit.name, Target.Jvm)
        addKmpModule(projects.lbloggerKermitCrashlytics.name, Target.Android)
        addModule(projects.lbtest.name)
    }
}

publishing {
    publications {
        create<MavenPublication>("lunabeeBom") {
            from(components["javaPlatform"])

            groupId = "studio.lunabee"
            version = versionName
            artifactId = "lunabee-bom"
        }
    }
}

fun DependencyConstraintHandlerScope.addModule(module: String) {
    api("$rootProject:${module.substringAfterLast(':')}:$versionName")
}

fun DependencyConstraintHandlerScope.addKmpModule(module: String, target: Target) {
    api("$rootProject:${module.substringAfterLast(':')}:$versionName")
    api("$rootProject:${module.substringAfterLast(':')}-iosarm64:$versionName")
    api("$rootProject:${module.substringAfterLast(':')}-iossimulatorarm64:$versionName")
    api("$rootProject:${module.substringAfterLast(':')}-iosx64:$versionName")
    when (target) {
        Target.Jvm -> api("$rootProject:${module.substringAfterLast(':')}-jvm:$versionName")
        Target.Android -> api("$rootProject:${module.substringAfterLast(':')}-android:$versionName")
    }
}

enum class Target {
    Jvm, Android
}
