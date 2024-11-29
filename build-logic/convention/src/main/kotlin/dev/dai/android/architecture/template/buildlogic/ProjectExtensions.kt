package dev.dai.android.architecture.template.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
  get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.library(name: String): MinimalExternalModuleDependency =
  findLibrary(name).get().get()

internal fun VersionCatalog.version(name: String): Int = findVersion(name).get().toString().toInt()
