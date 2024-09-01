import dev.dai.android.architecture.template.buildlogic.implementation
import dev.dai.android.architecture.template.buildlogic.ksp
import dev.dai.android.architecture.template.buildlogic.library
import dev.dai.android.architecture.template.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.google.devtools.ksp")
        apply("dagger.hilt.android.plugin")
      }

      dependencies {
        implementation(libs.library("hilt.android"))
        ksp(libs.library("hilt.compiler"))
      }
    }
  }
}
