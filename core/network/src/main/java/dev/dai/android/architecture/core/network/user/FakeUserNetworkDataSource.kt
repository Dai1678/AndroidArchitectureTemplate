package dev.dai.android.architecture.core.network.user

import dev.dai.android.architecture.core.network.user.response.UserResponse
import java.io.IOException

class FakeUserNetworkDataSource : UserNetworkDataSource {

  sealed class Status : UserNetworkDataSource {
    data object Success : Status() {
      override suspend fun getUsers(): List<UserResponse> =
        listOf(
          UserResponse(
            id = 1,
            name = "User1",
            email = "user1@dev.dai.com",
            phone = "1234567890",
            website = "https://dev.dai.com"
          ),
          UserResponse(
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
