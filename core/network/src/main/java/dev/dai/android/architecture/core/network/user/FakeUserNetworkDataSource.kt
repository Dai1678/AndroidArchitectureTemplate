package dev.dai.android.architecture.core.network.user

import dev.dai.android.architecture.core.network.user.response.UserResponse
import dev.dai.android.architecture.core.network.user.response.fake
import java.io.IOException

class FakeUserNetworkDataSource : UserNetworkDataSource {

  sealed class Status : UserNetworkDataSource {
    data object Success : Status() {
      override suspend fun getUsers(): List<UserResponse> =
        listOf(
          UserResponse.fake(),
          UserResponse.fake(
            id = 2,
            name = "User2",
            email = "user2@dev.dai.com",
            phone = "1234567890",
            website = "https://dev.dai.com"
          )
        )
    }

    data object Error : Status() {
      override suspend fun getUsers(): List<UserResponse> {
        throw IOException("Fake IO Exception")
      }
    }
  }

  private var status: Status = Status.Success

  fun setup(status: Status) {
    this.status = status
  }

  override suspend fun getUsers(): List<UserResponse> = status.getUsers()
}
