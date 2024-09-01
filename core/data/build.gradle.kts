plugins {
  alias(libs.plugins.android.architecture.template.android.library)
  alias(libs.plugins.android.architecture.template.android.hilt)
}

android {
  namespace = "dev.dai.android.architecture.template.core.data"
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:network"))
  testImplementation(project(":core:testing"))

  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.test)
}
