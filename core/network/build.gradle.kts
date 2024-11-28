plugins {
  alias(libs.plugins.project.android.library)
  alias(libs.plugins.project.android.hilt)
  id("kotlinx-serialization")
}

android {
  namespace = "dev.dai.android.architecture.template.core.network"
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:model"))

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp.logging)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.kotlin.serialization)

  testImplementation(libs.kotest.runner.junit5)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.extensions.mockserver)
}
