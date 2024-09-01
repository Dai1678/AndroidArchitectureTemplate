package dev.dai.android.architecture.build_logic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    compileSdk = 34

    defaultConfig {
      minSdk = 28
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }
  }

  extensions.configure<KotlinAndroidProjectExtension> {
    val warningsAsErrors: String? by project
    compilerOptions.apply {
      jvmTarget.set(JvmTarget.JVM_17)
      allWarningsAsErrors.set(warningsAsErrors.toBoolean())
    }
  }

  dependencies {
    // Required for emulator startup by AndroidJUnitRunner in instrumented tests
    add("androidTestImplementation", libs.library("androidx-test-espresso-core"))
  }
}
