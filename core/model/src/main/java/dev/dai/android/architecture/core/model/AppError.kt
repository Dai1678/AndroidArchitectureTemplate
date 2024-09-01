package dev.dai.android.architecture.core.model

sealed class AppError private constructor(cause: Throwable?) : Exception(cause) {
  class UnauthorizedException(cause: Throwable?) : AppError(cause = cause)
  class RequestErrorException(cause: Throwable?) : AppError(cause = cause)
  class ServerErrorException(cause: Throwable?) : AppError(cause = cause)
  class ServiceUnavailableException(cause: Throwable?) : AppError(cause = cause)
  class UnknownException(cause: Throwable?) : AppError(cause = cause)
}
