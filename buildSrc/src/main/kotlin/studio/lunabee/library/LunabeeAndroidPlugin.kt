package studio.lunabee.library

import org.gradle.api.Plugin
import org.gradle.api.Project

open class LunabeeAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configureAndroidPlugins()
        target.configureAndroid()
        target.configureDependencies()
    }
}
