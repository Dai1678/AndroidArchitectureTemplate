import com.android.build.api.dsl.Lint
import com.android.build.gradle.LibraryExtension
import dev.dai.android.architecture.template.buildlogic.configure
import dev.dai.android.architecture.template.buildlogic.implementation
import dev.dai.android.architecture.template.buildlogic.library
import dev.dai.android.architecture.template.buildlogic.libs
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
        lint(Lint::configure)
      }

      dependencies {
        implementation(libs.library("androidx.hilt.navigation.compose"))
        implementation(libs.library("androidx.lifecycle.runtimeCompose"))
        implementation(libs.library("androidx.lifecycle.viewModelCompose"))
      }
    }
  }
}
