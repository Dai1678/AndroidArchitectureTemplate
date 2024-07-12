package dev.dai.android.architecture.core.network.user

interface UserNetworkDataSource {
  suspend fun getUsers(): List<String>
}
