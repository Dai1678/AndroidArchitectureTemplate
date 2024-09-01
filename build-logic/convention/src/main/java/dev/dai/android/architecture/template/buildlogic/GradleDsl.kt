package dev.dai.android.architecture.template.buildlogic

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(
  artifact: Dependency,
) {
  add("implementation", artifact)
}

fun DependencyHandlerScope.implementation(
  artifact: MinimalExternalModuleDependency,
) {
  add("implementation", artifact)
}

fun DependencyHandlerScope.debugImplementation(
  artifact: MinimalExternalModuleDependency,
) {
  add("debugImplementation", artifact)
}

fun DependencyHandlerScope.androidTestImplementation(
  artifact: MinimalExternalModuleDependency,
) {
  add("androidTestImplementation", artifact)
}

fun DependencyHandlerScope.testImplementation(
  artifact: MinimalExternalModuleDependency,
) {
  add("testImplementation", artifact)
}

fun DependencyHandlerScope.ksp(
  artifact: MinimalExternalModuleDependency,
) {
  add("ksp", artifact)
}
