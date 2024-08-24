package dev.dai.android.architecture.core.test.repository

import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeUserRepository : UserRepository {

  private val _users = MutableSharedFlow<List<User>>()

  override val users: Flow<List<User>> = _users

  suspend fun emitUser(user: List<User>) {
    _users.emit(user)
  }
}
