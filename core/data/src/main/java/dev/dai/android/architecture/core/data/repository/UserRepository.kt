package dev.dai.android.architecture.core.data.repository

import dev.dai.android.architecture.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
  val users: Flow<List<User>>
}
