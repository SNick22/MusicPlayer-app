import com.azat.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("hilt").get().get().pluginId)
                // KAPT must go last to avoid build warnings.
                // See: https://stackoverflow.com/questions/70550883/warning-the-following-options-were-not-recognized-by-any-processor-dagger-f
                apply(libs.findPlugin("kapt").get().get().pluginId)
            }

            dependencies {
                "implementation"(libs.findLibrary("hilt").get())
                "kapt"(libs.findLibrary("hilt-compiler").get())
                "kaptAndroidTest"(libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}
