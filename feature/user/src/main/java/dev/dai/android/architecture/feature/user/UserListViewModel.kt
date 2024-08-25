package dev.dai.android.architecture.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.ui.STOP_TIMEOUT_MILLIS
import dev.dai.android.architecture.ui.UserMessageStateHolder
import dev.dai.android.architecture.ui.buildUiState
import dev.dai.android.architecture.ui.handleErrorAndRetry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
  val userMessageStateHolder: UserMessageStateHolder,
  userRepository: UserRepository,
) : ViewModel(), UserMessageStateHolder by userMessageStateHolder {

  private val usersStateFlow =
    userRepository.users
      .handleErrorAndRetry(
        actionLabelResId = null, // TODO retry strings resource id
        userMessageStateHolder = userMessageStateHolder,
      ) {
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
