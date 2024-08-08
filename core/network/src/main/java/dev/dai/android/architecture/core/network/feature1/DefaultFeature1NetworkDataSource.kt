package dev.dai.android.architecture.core.network.feature1

import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

private interface Feature1Api {
  @GET("/feature1/data")
  suspend fun getFeature1Data(): List<String>
}

class DefaultFeature1NetworkDataSource internal constructor(
  retrofit: Retrofit,
) : Feature1NetworkDataSource {

  private val feature1Api = retrofit.create<Feature1Api>()

  override suspend fun getFeature1Data(): List<String> =
    feature1Api.getFeature1Data()
}
