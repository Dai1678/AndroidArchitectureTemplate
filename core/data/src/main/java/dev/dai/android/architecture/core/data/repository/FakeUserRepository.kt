package dev.dai.android.architecture.core.data.repository

import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.model.fake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeUserRepository : UserRepository {
  override val users: Flow<List<User>> = flow {
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
  }.flowOn(Dispatchers.IO)
}
