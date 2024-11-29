package dev.dai.android.architecture.template.provider

import dev.dai.android.architecture.template.core.network.provider.ServerEnvironmentProvider
import javax.inject.Inject

class DefaultServerEnvironmentProvider @Inject constructor() : ServerEnvironmentProvider {
  override fun baseUrl(): String = ServerEnvironment.BASE_URL
}
