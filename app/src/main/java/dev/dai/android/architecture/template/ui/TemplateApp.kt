package dev.dai.android.architecture.template.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.dai.android.architecture.designsystem.theme.AndroidArchitectureTemplateTheme

@Composable
fun TemplateApp() {
  AndroidArchitectureTemplateTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      AppNavHost()
    }
  }
}
