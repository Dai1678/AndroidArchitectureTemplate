package dev.dai.android.architecture.template.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.template.core.model.provider.BuildConfigProvider
import dev.dai.android.architecture.template.provider.DefaultBuildConfigProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class BuildConfigProviderModule {
  @Binds
  abstract fun bindBuildConfigProvider(
    buildConfigProvider: DefaultBuildConfigProvider,
  ): BuildConfigProvider
}
