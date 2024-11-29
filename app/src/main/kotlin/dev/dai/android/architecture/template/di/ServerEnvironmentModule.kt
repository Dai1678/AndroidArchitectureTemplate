package dev.dai.android.architecture.template.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.template.core.network.provider.ServerEnvironmentProvider
import dev.dai.android.architecture.template.provider.DefaultServerEnvironmentProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class ServerEnvironmentModule {
  @Binds
  abstract fun provideServerEnvironmentProvider(
    provider: DefaultServerEnvironmentProvider,
  ): ServerEnvironmentProvider
}
