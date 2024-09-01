package dev.dai.android.architecture.core.data.mapper

import dev.dai.android.architecture.template.core.network.user.response.UserResponse
import dev.dai.android.architecture.template.core.model.User

fun UserResponse.toUser() = User(
  id = id,
  name = name,
  email = email,
  phone = phone,
  website = website
)
