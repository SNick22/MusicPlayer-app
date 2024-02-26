import com.azat.convention.configureKotlinJvm
import com.azat.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("org-jetbrains-kotlin-jvm").get().get().pluginId)
            }

            configureKotlinJvm()

            dependencies {
                "testImplementation"(libs.findLibrary("junit").get())
                "testImplementation"(libs.findLibrary("mockito").get())
                "testImplementation"(libs.findLibrary("mockito-kotlin").get())
            }
        }
    }
}