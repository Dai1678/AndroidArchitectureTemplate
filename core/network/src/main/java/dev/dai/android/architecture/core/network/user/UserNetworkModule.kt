package dev.dai.android.architecture.core.network.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserNetworkModule {
  @Provides
  @Singleton
  internal fun provideUserNetworkDataSource(
    retrofit: Retrofit,
  ): UserNetworkDataSource = DefaultUserNetworkDataSource(retrofit)
}
