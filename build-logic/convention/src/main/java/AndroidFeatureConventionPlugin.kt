import com.azat.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("azat.android.library")
                apply("azat.android.hilt")
                apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)
            }

            dependencies {
                add("implementation", project(":core:widgets"))
                add("implementation", project(":core:navigation"))
                add("implementation", project(":core:presentation"))
                add("implementation", project(":core:common-impl"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:database"))

                add("implementation", libs.findLibrary("androidx-lifecycle-runtime").get())
            }
        }
    }
}
