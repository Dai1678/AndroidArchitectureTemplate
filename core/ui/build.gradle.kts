plugins {
  alias(libs.plugins.project.android.library)
  alias(libs.plugins.project.android.library.compose)
  alias(libs.plugins.project.android.hilt)
}

android {
  namespace = "dev.dai.android.architecture.template.ui"
}

dependencies {
  implementation(libs.androidx.lifecycle.viewModel)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.timber)
}
