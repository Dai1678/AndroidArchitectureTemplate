package dev.dai.android.architecture.template.core.network.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.template.core.network.service.NetworkService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserNetworkModule {
  @Provides
  @Singleton
  internal fun provideUserNetworkDataSource(
    networkService: NetworkService,
    retrofit: Retrofit,
  ): UserNetworkDataSource = DefaultUserNetworkDataSource(networkService, retrofit)
}
