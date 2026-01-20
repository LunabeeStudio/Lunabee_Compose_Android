import org.gradle.api.internal.catalog.DelegatingProjectDependency

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
description = "Lunabee libraries bill of materials"
version = AndroidConfig.commonVersionName()

dependencies {
    constraints {
        addModule(projects.lbcore)
        addModule(projects.lbcoreAndroid)
        addModule(projects.lbcoreCompose)

        addModule(projects.lbktorCore)
        addModule(projects.lbktorJson)
        addModule(projects.lbktorKermit)

        addModule(projects.lbloadingCompose)
        addModule(projects.lbloadingChecks)
        addModule(projects.lbloadingCore)
        addModule(projects.lbloadingHilt)
        addModule(projects.lbloadingKoin)

        addModule(projects.lbloggerKermit)
        addModule(projects.lbloggerKermitCrashlytics)

        addModule(projects.lbextensions)
        addModule(projects.lbextensionsAndroid)
        addModule(projects.lbtest)
    }
}

fun DependencyConstraintHandlerScope.addModule(module: DelegatingProjectDependency) {
    api(project(":${module.name.substringAfterLast(':')}"))
}