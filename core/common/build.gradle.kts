plugins {
  alias(libs.plugins.project.android.library)
}

android {
  namespace = "dev.dai.android.architecture.template.common"
}

dependencies {
  implementation(libs.timber)
}
