package dev.dai.android.architecture.core.test.repository

import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.model.fake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class FakeUserRepository : UserRepository {

  private val _users = MutableSharedFlow<List<User>>()

  private var isError = false

  override val users: Flow<List<User>> = flow {
    if (isError) {
      throw IOException("Fake IO Exception")
    } else {
      emit(
        listOf(
          User.fake(),
          User.fake(
            id = 2,
            name = "User2",
            email = "user2@dev.dai.com",
            phone = "1234567890",
            website = "https://dev.dai.com",
          )
        )
      )
    }
  }.flowOn(Dispatchers.IO)

  fun setup(isError: Boolean) {
    this.isError = isError
  }

  suspend fun emitUser(user: List<User>) {
    _users.emit(user)
  }
}
