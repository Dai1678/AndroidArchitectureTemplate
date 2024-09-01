package dev.dai.android.architecture.template.ui

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

/**
 * SnackbarMessageEffect shows a snackbar message when a [UserMessage] is emitted by [userMessageStateHolder].
 */
@Composable
fun SnackbarMessageEffect(
  snackbarHostState: SnackbarHostState,
  userMessageStateHolder: UserMessageStateHolder,
) {
  userMessageStateHolder.messageUiState.userMessages.firstOrNull()?.let { userMessage ->
    val message =
      userMessage.message.ifEmpty { stringResource(id = R.string.core_ui_message_unknown_error) }
    val actionLabel = userMessage.actionLabelResId?.let { stringResource(id = it) }
    LaunchedEffect(userMessage) {
      val snackbarResult: SnackbarResult = if (userMessage.duration == null) {
        snackbarHostState.showSnackbar(
          message = message,
          actionLabel = actionLabel,
        )
      } else {
        snackbarHostState.showSnackbar(
          message = message,
          actionLabel = actionLabel,
          duration = userMessage.duration,
        )
      }
      userMessageStateHolder.messageShown(
        messageId = userMessage.id,
        userMessageResult = when (snackbarResult) {
          Dismissed -> UserMessageResult.Dismissed
          ActionPerformed -> UserMessageResult.ActionPerformed
        },
      )
    }
  }
}

data class UserMessage(
  val message: String,
  @StringRes val actionLabelResId: Int? = null,
  val duration: SnackbarDuration? = null,
  val id: Int = UUID.randomUUID().hashCode(),
  val userMessageResult: UserMessageResult? = null,
)

data class MessageUiState(
  val userMessages: List<UserMessage> = emptyList(),
)

class UserMessageStateHolderImpl : UserMessageStateHolder {
  private var _messageUiState by mutableStateOf(MessageUiState())
  override val messageUiState get() = _messageUiState
  override fun messageShown(messageId: Int, userMessageResult: UserMessageResult) {
    val messages = _messageUiState.userMessages.toMutableList()
    messages.indexOfFirst { it.id == messageId }.let { userMessageIndex ->
      if (userMessageIndex == -1) return@let
      messages[userMessageIndex] =
        messages[userMessageIndex].copy(userMessageResult = userMessageResult)
    }
    _messageUiState = _messageUiState.copy(userMessages = messages)
  }

  override suspend fun showMessage(
    message: String,
    @StringRes actionLabelResId: Int?,
    duration: SnackbarDuration?,
  ): UserMessageResult {
    val messages = _messageUiState.userMessages.toMutableList()
    val newMessage = UserMessage(message, actionLabelResId = actionLabelResId, duration = duration)
    messages.add(newMessage)
    _messageUiState = _messageUiState.copy(userMessages = messages)

    val messageResult = snapshotFlow {
      _messageUiState
    }.filter { messageState ->
      messageState.userMessages.find { it.id == newMessage.id }?.let { userMessage ->
        val messageResult = userMessage.userMessageResult
        messageResult != null
      } ?: false
    }
      .map { messageState ->
        val userMessage = checkNotNull(messageState.userMessages.find { it.id == newMessage.id })
        checkNotNull(userMessage.userMessageResult)
      }
      .first()

    val newMessages = _messageUiState.userMessages.toMutableList()
    newMessages.find { it.id == newMessage.id }?.let { userMessage ->
      newMessages.remove(userMessage)
    }
    _messageUiState = _messageUiState.copy(userMessages = newMessages)
    return messageResult
  }
}

interface UserMessageStateHolder {
  val messageUiState: MessageUiState
  fun messageShown(messageId: Int, userMessageResult: UserMessageResult)
  suspend fun showMessage(
    message: String,
    @StringRes actionLabelResId: Int? = null,
    duration: SnackbarDuration? = null,
  ): UserMessageResult
}

enum class UserMessageResult {
  Dismissed,
  ActionPerformed,
}
