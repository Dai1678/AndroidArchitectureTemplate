import dev.dai.android.architecture.template.build_logic.library
import dev.dai.android.architecture.template.build_logic.libs
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
        "implementation"(libs.library("hilt.android"))
        "ksp"(libs.library("hilt.compiler"))
      }
    }
  }
}
