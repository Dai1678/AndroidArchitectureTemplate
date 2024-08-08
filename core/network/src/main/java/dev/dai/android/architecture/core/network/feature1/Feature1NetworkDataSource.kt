package dev.dai.android.architecture.core.network.feature1

interface Feature1NetworkDataSource {
  suspend fun getFeature1Data(): List<String>
}
