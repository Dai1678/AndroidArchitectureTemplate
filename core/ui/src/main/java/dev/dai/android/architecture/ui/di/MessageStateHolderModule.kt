package dev.dai.android.architecture.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.ui.UserMessageStateHolder
import dev.dai.android.architecture.ui.UserMessageStateHolderImpl

@Module
@InstallIn(SingletonComponent::class)
class MessageStateHolderModule {
  @Provides
  fun provideMessageStateHolder(): UserMessageStateHolder = UserMessageStateHolderImpl()
}
