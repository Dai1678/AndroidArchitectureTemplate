package dev.dai.android.architecture.core.network.service

import dev.dai.android.architecture.core.model.AppError
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class NetworkService {
  inline operator fun <reified T : Any> invoke(
    block: () -> T,
  ): T = try {
    block()
  } catch (e: Throwable) {
    throw e.toAppError()
  }
}

fun Throwable.toAppError(): AppError {
  return when (this) {
    is AppError -> this
    is HttpException -> when (this.code()) {
      400 -> AppError.BadRequestException(this)
      401 -> AppError.UnauthorizedException(this)
      403 -> AppError.ForbiddenException(this)
      408 -> AppError.RequestTimeoutException(this)
      409 -> AppError.ConflictException(this)
      500 -> AppError.ServerErrorException(this)
      503 -> AppError.ServiceUnavailableException(this)
      else -> AppError.UnknownException(this)
    }

    is TimeoutException,
    is TimeoutCancellationException,
    is SocketTimeoutException,
    is UnknownHostException,
    -> AppError.RequestTimeoutException(this)

    else -> AppError.UnknownException(this)
  }
}
