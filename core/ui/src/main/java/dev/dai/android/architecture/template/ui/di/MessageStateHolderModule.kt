package dev.dai.android.architecture.template.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.android.architecture.template.ui.UserMessageStateHolder
import dev.dai.android.architecture.template.ui.UserMessageStateHolderImpl

@Module
@InstallIn(SingletonComponent::class)
class MessageStateHolderModule {
  @Provides
  fun provideMessageStateHolder(): UserMessageStateHolder = UserMessageStateHolderImpl()
}
