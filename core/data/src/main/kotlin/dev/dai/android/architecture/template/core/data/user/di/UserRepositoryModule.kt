package dev.dai.android.architecture.template.core.data.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.template.core.data.user.DefaultUserRepository
import dev.dai.android.architecture.template.core.data.user.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

  @Binds
  abstract fun bindsUserRepository(userRepository: DefaultUserRepository): UserRepository
}
