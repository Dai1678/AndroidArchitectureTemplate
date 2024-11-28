plugins {
  alias(libs.plugins.project.android.library)
  alias(libs.plugins.project.android.hilt)
}

android {
  namespace = "dev.dai.android.architecture.template.core.data"
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:network"))
  testImplementation(project(":core:testing"))

  implementation(libs.kotlinx.coroutines.android)

  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.kotest.runner.junit5)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.property)
}
