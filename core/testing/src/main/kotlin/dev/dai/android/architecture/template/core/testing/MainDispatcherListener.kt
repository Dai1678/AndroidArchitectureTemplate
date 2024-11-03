package dev.dai.android.architecture.template.core.testing

import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

class MainDispatcherListener : BeforeSpecListener, AfterSpecListener {
  override suspend fun beforeSpec(spec: Spec) {
    super.beforeSpec(spec)
    Dispatchers.setMain(UnconfinedTestDispatcher())
  }

  override suspend fun afterSpec(spec: Spec) {
    super.afterSpec(spec)
    Dispatchers.resetMain()
  }
}
