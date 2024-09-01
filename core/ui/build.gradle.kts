plugins {
  alias(libs.plugins.android.architecture.template.android.library)
  alias(libs.plugins.android.architecture.template.android.library.compose)
  alias(libs.plugins.android.architecture.template.android.hilt)
}

android {
  namespace = "dev.dai.android.architecture.template.ui"
}

dependencies {
  implementation(libs.androidx.lifecycle.viewModel)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.timber)
}
