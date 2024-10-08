package dev.dai.android.architecture.template.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.template.core.network.provider.ServerEnvironmentProvider
import dev.dai.android.architecture.template.core.network.service.NetworkService
import dev.dai.android.architecture.template.core.model.provider.BuildConfigProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

  @OptIn(ExperimentalSerializationApi::class)
  @Provides
  @Singleton
  fun provideNetworkJsonSetting(): Json = Json {
    namingStrategy = JsonNamingStrategy.SnakeCase
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    allowStructuredMapKeys = true
    prettyPrint = false
    explicitNulls = false
  }

  @Provides
  @Singleton
  fun provideNetworkService(): NetworkService = NetworkService()

  @Provides
  @Singleton
  fun provideOkHttpClient(
    buildConfigProvider: BuildConfigProvider,
  ): OkHttpClient {
    return OkHttpClient.Builder().apply {
      addInterceptor(
        HttpLoggingInterceptor().apply {
          level = if (buildConfigProvider.isDebugBuild) {
            HttpLoggingInterceptor.Level.BODY
          } else {
            HttpLoggingInterceptor.Level.NONE
          }
        }
      )
    }
      .build()
  }
}

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {
  @Provides
  @Singleton
  fun provideRetrofit(
    okHttpClient: OkHttpClient,
    networkJson: Json,
    serverEnvironmentProvider: ServerEnvironmentProvider,
  ): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(serverEnvironmentProvider.baseUrl())
      .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
      .build()
  }
}
