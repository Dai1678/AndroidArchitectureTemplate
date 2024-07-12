package dev.dai.android.architecture.core.network.user

import dev.dai.android.architecture.core.network.user.response.UserResponse
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

private interface UserApi {
  @GET("/users")
  suspend fun getUsers(): List<UserResponse>
}

class DefaultUserNetworkDataSource internal constructor(
  retrofit: Retrofit,
) : UserNetworkDataSource {

  private val userApi = retrofit.create<UserApi>()

  override suspend fun getUsers(): List<UserResponse> =
    userApi.getUsers()
}
