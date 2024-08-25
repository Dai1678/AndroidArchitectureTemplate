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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dev.dai.android.architecture.ui.R as UiR

@HiltViewModel
class UserListViewModel @Inject constructor(
  val userMessageStateHolder: UserMessageStateHolder,
  userRepository: UserRepository,
) : ViewModel(), UserMessageStateHolder by userMessageStateHolder {

  private val isRefresh = MutableStateFlow(false)

  private val usersStateFlow =
    userRepository.users
      .handleErrorAndRetry(
        actionLabelResId = UiR.string.label_retry_get,
        userMessageStateHolder = userMessageStateHolder,
        fallbackValue = emptyList(),
      )
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
        initialValue = null,
      )

  internal val uiState: StateFlow<UserListContentUiState> = buildUiState(
    isRefresh,
    usersStateFlow,
  ) { isRefresh, users ->
    UserListContentUiState(buildUserListUiState(isRefresh, users))
  }

  fun refresh() {
    viewModelScope.launch {
      isRefresh.update { true }
      delay(SIMULATE_NETWORK_DELAY_MILLIS)
      isRefresh.update { false }
    }
  }

  private fun buildUserListUiState(
    isRefresh: Boolean,
    users: List<User>?,
  ): UserListUiState {
    return if (users == null) {
      UserListUiState.Loading
    } else {
      UserListUiState.UserList(
        isRefresh = isRefresh,
        users = users,
      )
    }
  }

  companion object {
    private const val SIMULATE_NETWORK_DELAY_MILLIS = 1500L
  }
}
