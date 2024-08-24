import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  `kotlin-dsl`
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
  compilerOptions {
    jvmTarget = JvmTarget.JVM_17
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.compose.gradlePlugin)
  compileOnly(libs.detekt.gradlePlugin)
  compileOnly(libs.firebase.appdistribution.gradlePlugin)
  compileOnly(libs.firebase.crashlytics.gradlePlugin)
  compileOnly(libs.firebase.performance.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.ksp.gradlePlugin)
  compileOnly(libs.kover.gradlePugin)
}

gradlePlugin {
  plugins {
    register("androidApplicationCompose") {
      id = "android.architecture.template.android.application.compose"
      implementationClass = "AndroidApplicationComposeConventionPlugin"
    }
    register("androidApplicationFirebase") {
      id = "android.architecture.template.android.application.firebase"
      implementationClass = "AndroidApplicationFirebaseConventionPlugin"
    }
    register("androidApplication") {
      id = "android.architecture.template.android.application"
      implementationClass = "AndroidApplicationConventionPlugin"
    }
    register("androidLibraryCompose") {
      id = "android.architecture.template.android.library.compose"
      implementationClass = "AndroidLibraryComposeConventionPlugin"
    }
    register("androidLibrary") {
      id = "android.architecture.template.android.library"
      implementationClass = "AndroidLibraryConventionPlugin"
    }
    register("androidFeature") {
      id = "android.architecture.template.android.feature"
      implementationClass = "AndroidFeatureConventionPlugin"
    }
    register("androidHilt") {
      id = "android.architecture.template.android.hilt"
      implementationClass = "AndroidHiltConventionPlugin"
    }
    register("detekt") {
      id = "android.architecture.template.detekt"
      implementationClass = "DetektConventionPlugin"
    }
    register("kover") {
      id = "android.architecture.template.kover"
      implementationClass = "KoverConventionPlugin"
    }
  }
}
