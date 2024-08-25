plugins {
  alias(libs.plugins.android.architecture.template.android.library)
  alias(libs.plugins.android.architecture.template.android.hilt)
  id("kotlinx-serialization")
}

android {
  namespace = "dev.dai.android.architecture.core.network"
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:model"))

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp.logging)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.kotlin.serialization)
}
