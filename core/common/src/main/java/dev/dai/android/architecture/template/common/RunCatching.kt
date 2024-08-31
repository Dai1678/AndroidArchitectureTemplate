package dev.dai.android.architecture.template.common

import timber.log.Timber

/**
 * RuntimeException is an unchecked exception. It should not be caught.
 */
@Suppress("TooGenericExceptionCaught")
inline fun <R> runExceptionCatching(block: () -> R): Result<R> =
  try {
    Result.success(block())
  } catch (e: RuntimeException) {
    Timber.e(e)
    throw e
  } catch (e: Exception) {
    Timber.e(e)
    Result.failure(e)
  }
