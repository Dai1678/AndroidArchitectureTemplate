package dev.dai.android.architecture.ui

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry

fun <T> Flow<T>.handleErrorAndRetry(
  @StringRes actionLabelResId: Int? = null,
  userMessageStateHolder: UserMessageStateHolder,
  onCatch: suspend FlowCollector<T>.(cause: Throwable) -> Unit = {},
) = retry { throwable ->
  // TODO Timber.e(throwable)
  val messageResult = userMessageStateHolder.showMessage(
    message = throwable.message.orEmpty(),
    actionLabelResId = actionLabelResId
  )
  val retryPerformed = messageResult == UserMessageResult.ActionPerformed
  return@retry retryPerformed
}.catch { onCatch(it) }
