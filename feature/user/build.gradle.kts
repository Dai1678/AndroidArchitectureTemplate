plugins {
  alias(libs.plugins.android.architecture.template.android.feature)
  alias(libs.plugins.android.architecture.template.android.library.compose)
}

android {
  namespace = "dev.dai.android.architecture.template.feature.user"
}

dependencies {
  implementation(project(":core:common"))
  implementation(project(":core:model"))
  implementation(project(":core:data"))
  implementation(project(":core:ui"))
  implementation(project(":core:designsystem"))
  testImplementation(project(":core:testing"))

  implementation(libs.kotlinx.coroutines.android)

  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.mockk)
  testImplementation(libs.kotest.runner.junit5)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.property)
}
