package dev.dai.android.architecture.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.core.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
  userRepository: UserRepository,
) : ViewModel() {

  val uiState: StateFlow<UserUiState> =
    userRepository.users
      .map { users ->
        UserUiState(
          users = users,
        )
      }.catch { error ->
        // TODO
      }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        UserUiState(),
      )
}

data class UserUiState(
  val users: List<User> = emptyList(),
)
