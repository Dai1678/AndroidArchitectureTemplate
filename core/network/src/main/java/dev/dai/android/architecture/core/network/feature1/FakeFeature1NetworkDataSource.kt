package dev.dai.android.architecture.core.network.feature1

import java.io.IOException

class FakeFeature1NetworkDataSource : Feature1NetworkDataSource {

  sealed class Status : Feature1NetworkDataSource {
    data object Success : Status() {
      override suspend fun getFeature1Data(): List<String> =
        listOf("Fake data 1", "Fake data 2")
    }

    data object Error : Status() {
      override suspend fun getFeature1Data(): List<String> {
        throw IOException("Fake IO Exception")
      }
    }
  }

  private var status: Status = Status.Success

  fun setup(status: Status) {
    this.status = status
  }

  override suspend fun getFeature1Data(): List<String> =
    status.getFeature1Data()
}
