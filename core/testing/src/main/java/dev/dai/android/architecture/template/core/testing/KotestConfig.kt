package dev.dai.android.architecture.template.core.testing

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode

// https://kotest.io/docs/framework/integrations/mocking.html#option-3---tweak-the-isolationmode
class KotestConfig : AbstractProjectConfig() {
  override val coroutineTestScope = true
  override val isolationMode: IsolationMode = IsolationMode.InstancePerTest
}
