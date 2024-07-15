package dev.dai.android.architecture.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.core.data.repository.DefaultUserRepository
import dev.dai.android.architecture.core.data.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindsUserRepository(userRepository: DefaultUserRepository): UserRepository
}
