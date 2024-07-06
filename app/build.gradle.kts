plugins {
  alias(libs.plugins.android.architecture.template.android.application)
  alias(libs.plugins.android.architecture.template.android.application.compose)
  alias(libs.plugins.android.architecture.template.android.application.jacoco)
  alias(libs.plugins.android.architecture.template.android.hilt)
}

android {
  namespace = "dev.dai.android.architecture.template"

  defaultConfig {
    applicationId = "dev.dai.android.architecture.template"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
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
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.lifecycle.runtimeCompose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.coil.kt.compose)

  ksp(libs.hilt.compiler)

  kspTest(libs.hilt.compiler)

  testImplementation(libs.hilt.android.testing)
  testImplementation(libs.robolectric)

  androidTestImplementation(kotlin("test"))
  androidTestImplementation(libs.androidx.test.espresso.core)
  androidTestImplementation(libs.androidx.navigation.testing)
  androidTestImplementation(libs.androidx.test.ext)

  androidTestImplementation(libs.hilt.android.testing)
}
