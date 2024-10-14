package dev.dai.android.architecture.core.data.user.fake

import dev.dai.android.architecture.core.data.user.UserRepository
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeUserRepository : UserRepository {

  private val _users = MutableSharedFlow<List<User>>()

  override fun usersStream(): Flow<List<User>> = _users

  override suspend fun refresh() {
    _users.emit(listOf(User.fake()))
  }

  suspend fun emitUser(user: List<User>) {
    _users.emit(user)
  }
}
