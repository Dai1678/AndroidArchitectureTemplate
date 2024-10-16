import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.Lint
import dev.dai.android.architecture.template.buildlogic.configure
import dev.dai.android.architecture.template.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
        apply("android.architecture.template.detekt")
      }

      extensions.configure<ApplicationExtension> {
        configureKotlinAndroid(this)

        defaultConfig {
          targetSdk = 34
          versionCode = 1
          versionName = "1.0"
          vectorDrawables {
            useSupportLibrary = true
          }
        }

        lint(Lint::configure)
      }
    }
  }
}
