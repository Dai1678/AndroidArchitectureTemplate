package dev.dai.android.architecture.core.network.service

import dev.dai.android.architecture.core.model.AppError
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class NetworkService {
  @Suppress("SwallowedException, TooGenericExceptionCaught")
  inline operator fun <reified T : Any> invoke(
    block: () -> T,
  ): T = try {
    block()
  } catch (e: Throwable) {
    throw e.toAppError()
  }
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
    is TimeoutCancellationException,
    is SocketTimeoutException,
    is UnknownHostException,
    -> AppError.RequestErrorException(this)

    else -> AppError.UnknownException(this)
  }
}
