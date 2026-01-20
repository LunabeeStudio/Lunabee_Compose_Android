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
        addModule(projects.core)
        addModule(projects.coreAndroid)
        addModule(projects.coreCompose)

        addModule(projects.ktorCore)
        addModule(projects.ktorJson)
        addModule(projects.ktorKermit)

        addModule(projects.loadingCompose)
        addModule(projects.loadingChecks)
        addModule(projects.loadingCore)
        addModule(projects.loadingHilt)
        addModule(projects.loadingKoin)

        addModule(projects.loggerKermit)
        addModule(projects.loggerKermitCrashlytics)

        addModule(projects.extension)
        addModule(projects.extensionAndroid)
        addModule(projects.test)
    }
}

fun DependencyConstraintHandlerScope.addModule(module: DelegatingProjectDependency) {
    api(project(":${module.name.substringAfterLast(':')}"))
}