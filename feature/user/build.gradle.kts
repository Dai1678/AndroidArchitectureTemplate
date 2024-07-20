plugins {
  alias(libs.plugins.android.architecture.template.android.feature)
  alias(libs.plugins.android.architecture.template.android.library.compose)
  alias(libs.plugins.android.architecture.template.android.library.jacoco)
}

android {
  namespace = "dev.dai.android.architecture.feature.user"
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:data"))

  testImplementation(project(":core:test"))

  implementation(libs.kotlinx.coroutines.android)
  testImplementation(libs.kotlinx.coroutines.test)
}
