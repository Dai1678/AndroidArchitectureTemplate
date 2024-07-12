package dev.dai.android.architecture.core.network.user

import java.io.IOException

class FakeUserNetworkDataSource : UserNetworkDataSource {

  sealed class Status : UserNetworkDataSource {
    data object Success : Status() {
      override suspend fun getUsers(): List<String> =
        listOf("Fake data 1", "Fake data 2")
    }

    data object Error : Status() {
      override suspend fun getUsers(): List<String> {
        throw IOException("Fake IO Exception")
      }
    }
  }

  private var status: Status = Status.Success

  fun setup(status: Status) {
    this.status = status
  }

  override suspend fun getUsers(): List<String> =
    status.getUsers()
}
