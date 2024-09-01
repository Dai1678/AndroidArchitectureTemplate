package dev.dai.android.architecture.template.build_logic

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
      add("implementation", platform(libs.library("androidx-compose-bom")))
      add("implementation", libs.library("androidx-compose-animation"))
      add("implementation", libs.library("androidx-compose-foundation"))
      add("implementation", libs.library("androidx-compose-material-iconsExtended"))
      add("implementation", libs.library("androidx-compose-material3"))
      add("implementation", libs.library("androidx-compose-material3-windowSizeClass"))
      add("implementation", libs.library("androidx-compose-runtime"))
      add("implementation", libs.library("androidx-compose-ui-tooling-preview"))
      add("debugImplementation", libs.library("androidx-compose-ui-tooling"))
    }

    testOptions {
      unitTests {
        // For Robolectric
        isIncludeAndroidResources = true
      }
    }
  }
}
