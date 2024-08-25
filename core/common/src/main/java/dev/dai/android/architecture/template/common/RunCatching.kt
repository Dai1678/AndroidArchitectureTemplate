package dev.dai.android.architecture.template.common

/**
 * RuntimeException is an unchecked exception. It should not be caught.
 */
inline fun <R> runExceptionCatching(block: () -> R): Result<R> =
  try {
    Result.success(block())
  } catch (e: RuntimeException) {
    throw e
  } catch (e: Exception) {
    Result.failure(e)
  }
