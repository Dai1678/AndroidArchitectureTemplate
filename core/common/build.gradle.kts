plugins {
  alias(libs.plugins.android.architecture.template.android.library)
}

android {
  namespace = "dev.dai.android.architecture.template.common"
}

dependencies {
  implementation(libs.timber)
}