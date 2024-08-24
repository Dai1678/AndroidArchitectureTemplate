plugins {
  alias(libs.plugins.android.architecture.template.android.library)
  alias(libs.plugins.android.architecture.template.android.library.compose)
}

android {
  namespace = "dev.dai.android.architecture.ui"
}

dependencies {
  implementation(libs.androidx.lifecycle.viewModel)
  implementation(libs.kotlinx.coroutines.android)
}
