package dev.dai.android.architecture.template.core.network.user

import dev.dai.android.architecture.template.core.network.service.NetworkService
import dev.dai.android.architecture.template.core.network.user.response.UserResponse
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

private interface UserApi {
  @GET("user/users.json")
  suspend fun getUsers(): List<UserResponse>
}

internal class DefaultUserNetworkDataSource internal constructor(
  private val networkService: NetworkService,
  retrofit: Retrofit,
) : UserNetworkDataSource {

  private val userApi = retrofit.create<UserApi>()

  override suspend fun getUsers(): List<UserResponse> {
    return networkService {
      userApi.getUsers()
    }
  }
}
