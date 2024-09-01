package dev.dai.android.architecture.ui

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber

fun <T> Flow<T>.handleErrorAndRetry(
  @StringRes actionLabelResId: Int? = null,
  userMessageStateHolder: UserMessageStateHolder,
  fallbackValue: T?,
) = retryWhen { throwable, _ ->
  Timber.e(throwable)
  fallbackValue?.let { emit(it) }
  val messageResult = userMessageStateHolder.showMessage(
    message = throwable.message.orEmpty(),
    actionLabelResId = actionLabelResId
  )
  val retryPerformed = messageResult == UserMessageResult.ActionPerformed
  return@retryWhen retryPerformed
}.catch { /* Do nothing if the user dose not retry. */ }
