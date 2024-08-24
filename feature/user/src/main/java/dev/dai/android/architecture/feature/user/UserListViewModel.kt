package dev.dai.android.architecture.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.ui.STOP_TIMEOUT_MILLIS
import dev.dai.android.architecture.ui.buildUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
  userRepository: UserRepository,
) : ViewModel() {

  private val usersStateFlow =
    userRepository.users
      .catch {
        // TODO Handle error
        emit(emptyList())
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
        initialValue = null,
      )

  internal val uiState: StateFlow<UserListContentUiState> = buildUiState(
    usersStateFlow,
  ) { users ->
    UserListContentUiState(buildUserListUiState(users))
  }

  private fun buildUserListUiState(users: List<User>?): UserListUiState {
    return if (users == null) {
      UserListUiState.Loading
    } else {
      UserListUiState.UserList(
        users = users,
      )
    }
  }
}
