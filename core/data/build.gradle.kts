plugins {
  alias(libs.plugins.android.architecture.template.android.library)
  alias(libs.plugins.android.architecture.template.android.library.jacoco)
  alias(libs.plugins.android.architecture.template.android.hilt)
}

android {
  namespace = "dev.dai.android.architecture.core.data"
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:network"))

  testImplementation(project(":core:network"))

  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.test)
}
