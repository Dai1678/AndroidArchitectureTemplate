package dev.dai.android.architecture.core.network.user

import dev.dai.android.architecture.core.network.user.response.UserResponse
import dev.dai.android.architecture.core.network.user.response.fake
import kotlinx.coroutines.delay
import retrofit2.Retrofit

//private interface UserApi {
//  @GET("/users")
//  suspend fun getUsers(): List<UserResponse>
//}

class DefaultUserNetworkDataSource internal constructor(
  retrofit: Retrofit,
) : UserNetworkDataSource {

  // private val userApi = retrofit.create<UserApi>()

  override suspend fun getUsers(): List<UserResponse> {
    // userApi.getUsers()
    delay(SIMULATE_NETWORK_DELAY_MILLIS)
    return buildList {
      repeat(USER_RESPONSE_SIZE) {
        UserResponse.fake(
          id = it,
          name = "User$it",
          email = "user$it@dev.dai.com",
          phone = "1234567890",
          website = "https://dev.dai.com",
        ).let { userResponse -> add(userResponse) }
      }
    }
  }

  companion object {
    private const val SIMULATE_NETWORK_DELAY_MILLIS = 1500L
    private const val USER_RESPONSE_SIZE = 20
  }
}
