package dev.dai.android.architecture.template.core.model

data class User(
  val id: Int,
  val name: String,
  val email: String,
  val phone: String,
  val website: String,
) {
  companion object
}

fun User.Companion.fake(
  id: Int = 1,
  name: String = "User1",
  email: String = "user1@dev.dai.com",
  phone: String = "1234567890",
  website: String = "https://dev.dai.com",
): User {
  return User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    website = website,
  )
}
