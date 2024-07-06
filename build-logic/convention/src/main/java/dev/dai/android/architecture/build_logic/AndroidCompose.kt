package dev.dai.android.architecture.build_logic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    buildFeatures {
      compose = true
    }

    dependencies {
      val bom = libs.findLibrary("androidx-compose-bom").get()
      add("implementation", platform(bom))
      add("androidTestImplementation", platform(bom))
      add("implementation", libs.findLibrary("androidx-compose-animation").get())
      add("implementation", libs.findLibrary("androidx-compose-foundation").get())
      add("implementation", libs.findLibrary("androidx-compose-material-iconsExtended").get())
      add("implementation", libs.findLibrary("androidx-compose-material3").get())
      add("implementation", libs.findLibrary("androidx-compose-material3-windowSizeClass").get())
      add("implementation", libs.findLibrary("androidx-compose-runtime").get())
      add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
      add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
    }

    testOptions {
      unitTests {
        // For Robolectric
        isIncludeAndroidResources = true
      }
    }
  }
}
