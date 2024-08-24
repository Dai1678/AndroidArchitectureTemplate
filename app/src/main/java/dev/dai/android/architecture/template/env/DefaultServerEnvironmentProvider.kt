package dev.dai.android.architecture.template.env

import dev.dai.android.architecture.core.network.provider.ServerEnvironmentProvider
import javax.inject.Inject

class DefaultServerEnvironmentProvider @Inject constructor() : ServerEnvironmentProvider {
  override fun baseUrl(): String = ServerEnvironment.BASE_URL
}
