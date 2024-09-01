package dev.dai.android.architecture.core.data.repository

import dev.dai.android.architecture.core.data.mapper.toUser
import dev.dai.android.architecture.template.core.network.user.UserNetworkDataSource
import dev.dai.android.architecture.template.core.network.user.response.UserResponse
import dev.dai.android.architecture.template.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
  private val userDataSource: UserNetworkDataSource,
) : UserRepository {

  private val _users = MutableStateFlow(emptyList<User>())

  override fun users(): Flow<List<User>> {
    return _users.onStart {
      if (_users.value.isEmpty()) {
        refresh()
      }
    }
  }

  override suspend fun refresh() {
    _users.value = userDataSource.getUsers()
      .map(UserResponse::toUser)
  }
}
