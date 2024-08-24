package dev.dai.android.architecture.core.data.mapper

import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.network.user.response.UserResponse

fun UserResponse.toUser() = User(
  id = id,
  name = name,
  email = email,
  phone = phone,
  website = website
)
