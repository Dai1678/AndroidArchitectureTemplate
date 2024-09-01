package dev.dai.android.architecture.template.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.dai.android.architecture.template.designsystem.theme.AndroidArchitectureTemplateTheme

@Composable
fun LoadingContent() {
  Surface(
    color = Color.Transparent,
    modifier = Modifier.fillMaxSize()
  ) {
    Box(contentAlignment = Alignment.Center) {
      CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
    }
  }
}

@Preview
@Composable
private fun PreviewLoadingContent() {
  AndroidArchitectureTemplateTheme {
    Surface {
      LoadingContent()
    }
  }
}
