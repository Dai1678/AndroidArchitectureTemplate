import com.android.build.api.dsl.Lint
import com.android.build.gradle.LibraryExtension
import dev.dai.android.architecture.build_logic.configure
import dev.dai.android.architecture.build_logic.library
import dev.dai.android.architecture.build_logic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("android.architecture.template.android.library")
        apply("android.architecture.template.android.hilt")
        apply("android.architecture.template.detekt")
      }
      extensions.configure<LibraryExtension> {
        testOptions.animationsDisabled = true
        lint(Lint::configure)
      }

      dependencies {
        add("implementation", project(":core:common"))
        add("implementation", project(":core:ui"))
        add("implementation", project(":core:designsystem"))

        add("implementation", libs.library("androidx.hilt.navigation.compose"))
        add("implementation", libs.library("androidx.lifecycle.runtimeCompose"))
        add("implementation", libs.library("androidx.lifecycle.viewModelCompose"))
      }
    }
  }
}
