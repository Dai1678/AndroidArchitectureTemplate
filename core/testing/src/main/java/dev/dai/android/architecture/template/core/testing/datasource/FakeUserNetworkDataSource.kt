package dev.dai.android.architecture.template.core.testing.datasource

import dev.dai.android.architecture.template.core.network.user.UserNetworkDataSource
import dev.dai.android.architecture.template.core.network.user.response.UserResponse
import dev.dai.android.architecture.template.core.network.user.response.fake

class FakeUserNetworkDataSource : UserNetworkDataSource {
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
