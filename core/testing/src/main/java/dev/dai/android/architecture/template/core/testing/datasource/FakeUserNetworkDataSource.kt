package dev.dai.android.architecture.template.core.testing.datasource

import dev.dai.android.architecture.template.core.network.user.UserNetworkDataSource
import dev.dai.android.architecture.template.core.network.user.response.UserResponse

class FakeUserNetworkDataSource : UserNetworkDataSource {
  private val users: MutableList<UserResponse> = mutableListOf()

  override suspend fun getUsers(): List<UserResponse> = users

  fun setUsers(vararg users: UserResponse) {
    this.users.clear()
    this.users.addAll(users)
  }
}
