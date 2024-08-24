package dev.dai.android.architecture.core.data.repository

import dev.dai.android.architecture.core.data.mapper.toUser
import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.network.user.UserNetworkDataSource
import dev.dai.android.architecture.core.network.user.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
  private val userDataSource: UserNetworkDataSource,
) : UserRepository {

  override val users: Flow<List<User>> = flow {
    val usersResponse = userDataSource.getUsers()
    emit(usersResponse.map(UserResponse::toUser))
  }
}
