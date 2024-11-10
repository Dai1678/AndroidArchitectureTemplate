package dev.dai.android.architecture.template.core.data.user

import dev.dai.android.architecture.template.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
  fun usersStream(): Flow<List<User>>
  suspend fun refresh()
}
