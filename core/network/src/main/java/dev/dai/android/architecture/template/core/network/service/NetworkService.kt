package dev.dai.android.architecture.template.core.network.service

import dev.dai.android.architecture.template.common.runExceptionCatching
import dev.dai.android.architecture.template.core.model.AppError
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class NetworkService {
  @Suppress("SwallowedException, TooGenericExceptionCaught")
  inline operator fun <reified T : Any> invoke(
    block: () -> T,
  ): T = runExceptionCatching {
    block()
  }.fold(
    onSuccess = { it },
    onFailure = { throw it.toAppError() },
  )
}

@Suppress("MagicNumber")
fun Throwable.toAppError(): AppError {
  return when (this) {
    is AppError -> this
    is HttpException -> when (this.code()) {
      401 -> AppError.UnauthorizedException(this)
      in 400..499 -> AppError.RequestErrorException(this)
      503 -> AppError.ServiceUnavailableException(this)
      in 500..599 -> AppError.ServerErrorException(this)
      else -> AppError.UnknownException(this)
    }

    is TimeoutException,
    is SocketTimeoutException,
    is UnknownHostException,
    -> AppError.RequestErrorException(this)

    else -> AppError.UnknownException(this)
  }
}
