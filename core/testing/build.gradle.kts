plugins {
  alias(libs.plugins.android.architecture.template.android.library)
}

android {
  namespace = "dev.dai.android.architecture.template.core.testing"
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:network"))
  implementation(project(":core:data"))

  api(libs.kotlinx.coroutines.test)

  implementation(libs.androidx.test.rules)
}
