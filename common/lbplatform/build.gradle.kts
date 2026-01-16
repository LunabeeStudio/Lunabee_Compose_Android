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
        addModule(projects.lbextensionsAndroid.name)
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
    Jvm,
    Android,
}
