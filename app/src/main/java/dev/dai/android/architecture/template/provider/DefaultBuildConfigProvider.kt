package dev.dai.android.architecture.template.provider

import dev.dai.android.architecture.core.model.provider.BuildConfigProvider
import dev.dai.android.architecture.template.BuildConfig
import javax.inject.Inject

class DefaultBuildConfigProvider @Inject constructor() : BuildConfigProvider {
  override val isDebugBuild: Boolean = BuildConfig.DEBUG
}
