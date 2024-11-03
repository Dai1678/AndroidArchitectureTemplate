package dev.dai.android.architecture.template.buildlogic

import com.android.build.api.dsl.Lint

fun Lint.configure() {
  abortOnError = false
  xmlReport = true
  htmlReport = true
  sarifReport = true
  checkDependencies = true
}
