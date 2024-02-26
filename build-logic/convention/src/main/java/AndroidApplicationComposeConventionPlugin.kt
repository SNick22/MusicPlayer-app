import com.android.build.api.dsl.ApplicationExtension
import com.azat.convention.configureAndroidCompose
import com.azat.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("azat.android.application")
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)

            dependencies {
                "implementation"(libs.findLibrary("activity-compose").get())
            }
        }
    }

}
