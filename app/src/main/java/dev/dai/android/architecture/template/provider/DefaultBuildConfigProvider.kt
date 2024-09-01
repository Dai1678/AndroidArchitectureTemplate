package dev.dai.android.architecture.template.provider

import dev.dai.android.architecture.template.BuildConfig
import dev.dai.android.architecture.template.core.model.provider.BuildConfigProvider
import javax.inject.Inject

class DefaultBuildConfigProvider @Inject constructor() : BuildConfigProvider {
  override val isDebugBuild: Boolean = BuildConfig.DEBUG
}
