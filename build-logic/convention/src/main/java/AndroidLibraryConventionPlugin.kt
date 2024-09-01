import com.android.build.api.dsl.Lint
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import dev.dai.android.architecture.template.buildlogic.configure
import dev.dai.android.architecture.template.buildlogic.configureKotlinAndroid
import dev.dai.android.architecture.template.buildlogic.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
        apply("android.architecture.template.detekt")
      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = 34
        testOptions.animationsDisabled = true
        // The resource prefix is derived from the module name,
        // so resources inside ":core:module1" must be prefixed with "core_module1_"
        resourcePrefix =
          path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
            .lowercase() + "_"
        lint(Lint::configure)
      }
      extensions.configure<LibraryAndroidComponentsExtension> {
        disableUnnecessaryAndroidTests(target)
      }
      dependencies {
        add("testImplementation", kotlin("test"))
        add("androidTestImplementation", kotlin("test"))
      }
    }
  }
}
