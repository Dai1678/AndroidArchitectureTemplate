package dev.dai.android.architecture.core.network.user

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

private interface UserApi {
  @GET("/users")
  suspend fun getFeature1Data(): List<String>
}

class DefaultUserNetworkDataSource internal constructor(
  retrofit: Retrofit,
) : UserNetworkDataSource {

  private val userApi = retrofit.create<UserApi>()

  override suspend fun getUsers(): List<String> =
    userApi.getFeature1Data()
}
