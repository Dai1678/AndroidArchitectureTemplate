package dev.dai.android.architecture.build_logic

import com.android.build.api.dsl.Lint

fun Lint.configure() {
  abortOnError = false
  xmlReport = true
  htmlReport = true
  sarifReport = true
  checkDependencies = true
}
