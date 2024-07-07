package dev.dai.android.architecture.core.network.feature1

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Feature1NetworkModule {
  @Provides
  @Singleton
  internal fun provideFeature1NetworkDataSource(
    retrofit: Retrofit,
  ): Feature1NetworkDataSource = DefaultFeature1NetworkDataSource(retrofit)
}
