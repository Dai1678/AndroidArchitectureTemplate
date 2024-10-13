package dev.dai.android.architecture.template.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.android.architecture.core.data.user.UserRepository
import dev.dai.android.architecture.template.common.runExceptionCatching
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.ui.STOP_TIMEOUT_MILLIS
import dev.dai.android.architecture.template.ui.UserMessageStateHolder
import dev.dai.android.architecture.template.ui.buildUiState
import dev.dai.android.architecture.template.ui.handleErrorAndRetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import dev.dai.android.architecture.template.ui.R as UiR

@HiltViewModel
class UserListViewModel @Inject constructor(
  val userMessageStateHolder: UserMessageStateHolder,
  private val userRepository: UserRepository,
) : ViewModel(), UserMessageStateHolder by userMessageStateHolder {

  private val isRefresh = MutableStateFlow(false)

  private val usersStateFlow =
    userRepository.users()
      .handleErrorAndRetry(
        actionLabelResId = UiR.string.core_ui_label_retry_get,
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
      runExceptionCatching {
        userRepository.refresh()
      }.fold(
        onSuccess = {
          isRefresh.update { false }
        },
        onFailure = { throwable ->
          isRefresh.update { false }
          userMessageStateHolder.showMessage(
            message = throwable.message.orEmpty()
          )
        }
      )
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
}
