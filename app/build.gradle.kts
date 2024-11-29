 plugins {
  alias(libs.plugins.project.android.application)
  alias(libs.plugins.project.android.application.compose)
  alias(libs.plugins.project.android.application.firebase)
  alias(libs.plugins.project.android.hilt)
  alias(libs.plugins.project.kover)
}

android {
  namespace = "dev.dai.android.architecture.template"

  defaultConfig {
    applicationId = "dev.dai.android.architecture.template"
  }

  buildFeatures {
    buildConfig = true
  }

  buildTypes {
    debug {
      applicationIdSuffix = ".debug"
      isDebuggable = true
      isMinifyEnabled = false
    }
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(project(":core:designsystem"))
  implementation(project(":core:model"))
  implementation(project(":core:network"))
  implementation(project(":feature:user"))

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.lifecycle.runtimeCompose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.coil.kt.compose)
  implementation(libs.timber)

  ksp(libs.hilt.compiler)

  androidTestImplementation(libs.androidx.test.ext)
}
