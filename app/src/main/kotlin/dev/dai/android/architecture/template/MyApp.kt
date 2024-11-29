package dev.dai.android.architecture.template

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {
  override fun onCreate() {
    super.onCreate()
    setupTimber()
  }
}

private fun setupTimber() {
  if (BuildConfig.DEBUG) {
    Timber.plant(Timber.DebugTree())
  }
}
