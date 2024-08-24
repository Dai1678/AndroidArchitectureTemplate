package dev.dai.android.architecture.template.di

import dagger.Binds
import dagger.Module
import dev.dai.android.architecture.core.model.provider.BuildConfigProvider
import dev.dai.android.architecture.template.provider.DefaultBuildConfigProvider
import javax.inject.Singleton

@Module
@Singleton
abstract class BuildConfigProviderModule {
  @Binds
  abstract fun bindBuildConfigProvider(
    buildConfigProvider: DefaultBuildConfigProvider,
  ): BuildConfigProvider
}
