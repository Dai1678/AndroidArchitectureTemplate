package dev.dai.android.architecture.template.core.network.user.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
  val id: Int,
  val name: String,
  val email: String,
  val phone: String,
  val website: String,
) {
  companion object
}

fun UserResponse.Companion.fake(
  id: Int = 1,
  name: String = "User1",
  email: String = "user1@dev.dai.com",
  phone: String = "1234567890",
  website: String = "https://dev.dai.com",
): UserResponse {
  return UserResponse(
    id = id,
    name = name,
    email = email,
    phone = phone,
    website = website,
  )
}
