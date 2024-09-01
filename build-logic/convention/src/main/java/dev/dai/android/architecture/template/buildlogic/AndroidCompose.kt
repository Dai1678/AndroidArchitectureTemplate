package dev.dai.android.architecture.template.buildlogic

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
      implementation(platform(libs.library("androidx-compose-bom")))
      implementation(libs.library("androidx-compose-animation"))
      implementation(libs.library("androidx-compose-foundation"))
      implementation(libs.library("androidx-compose-material-iconsExtended"))
      implementation(libs.library("androidx-compose-material3"))
      implementation(libs.library("androidx-compose-material3-windowSizeClass"))
      implementation(libs.library("androidx-compose-runtime"))
      implementation(libs.library("androidx-compose-ui-tooling-preview"))
      debugImplementation(libs.library("androidx-compose-ui-tooling"))
    }

    testOptions {
      unitTests {
        // For Robolectric
        isIncludeAndroidResources = true
      }
    }
  }
}
