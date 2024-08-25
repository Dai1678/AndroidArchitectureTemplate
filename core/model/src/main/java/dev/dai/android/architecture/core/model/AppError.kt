package dev.dai.android.architecture.core.model

sealed class AppError private constructor(cause: Throwable?) : RuntimeException(cause) {
  class BadRequestException(cause: Throwable?) : AppError(cause = cause)
  class UnauthorizedException(cause: Throwable?) : AppError(cause = cause)
  class ForbiddenException(cause: Throwable?) : AppError(cause = cause)
  class RequestTimeoutException(cause: Throwable?) : AppError(cause = cause)
  class ConflictException(cause: Throwable?) : AppError(cause = cause)
  class ServerErrorException(cause: Throwable?) : AppError(cause = cause)
  class ServiceUnavailableException(cause: Throwable?) : AppError(cause = cause)
  class UnknownException(cause: Throwable?) : AppError(cause = cause)
}
