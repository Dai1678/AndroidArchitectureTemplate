package dev.dai.android.architecture.core.network.user

import dev.dai.android.architecture.core.network.user.response.UserResponse

interface UserNetworkDataSource {
  suspend fun getUsers(): List<UserResponse>
}
