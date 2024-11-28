package dev.dai.android.architecture.template.core.data.user.mapper

import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.network.user.response.UserResponse

internal fun UserResponse.toUser() = User(
  id = id,
  name = name,
  email = email,
  phone = phone,
  website = website
)
