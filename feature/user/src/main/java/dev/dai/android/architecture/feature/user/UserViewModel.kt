package dev.dai.android.architecture.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
  userRepository: UserRepository,
) : ViewModel() {

  private val viewModelState: MutableStateFlow<UserViewModelState> =
    MutableStateFlow(UserViewModelState(isLoading = true))

  val uiState: StateFlow<UserUiState> = combine(
    viewModelState,
    userRepository.users,
  ) { state, users ->
    state.copy(users = users, isLoading = false).toUiState()
  }.catch { error ->
    if (viewModelState.value.toUiState() is UserUiState.NoUsers) {
      emit(UserUiState.EmptyError(error))
    } else {
      viewModelState.update {
        it.copy(event = UserUiEvent.ShowSnackBar)
      }
    }
  }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5_000),
    viewModelState.value.toUiState(),
  )

  fun consumeEvent() {
    viewModelState.update {
      it.copy(event = null)
    }
  }
}

private data class UserViewModelState(
  val users: List<User> = emptyList(),
  val isLoading: Boolean = false,
  val event: UserUiEvent? = null,
) {
  fun toUiState(): UserUiState =
    if (users.isEmpty()) {
      UserUiState.NoUsers(isLoading, event)
    } else {
      UserUiState.HasUsers(users, isLoading, event)
    }
}

sealed interface UserUiState {
  val isLoading: Boolean
  val event: UserUiEvent?

  data class NoUsers(
    override val isLoading: Boolean,
    override val event: UserUiEvent?,
  ) : UserUiState

  data class HasUsers(
    val users: List<User>,
    override val isLoading: Boolean,
    override val event: UserUiEvent?,
  ) : UserUiState

  data class EmptyError(
    val throwable: Throwable,
    val message: String = "通信に失敗しました。再度お試しください。",
    override val isLoading: Boolean = false,
    override val event: UserUiEvent? = null,
  ) : UserUiState
}

sealed interface UserUiEvent {
  data object ShowSnackBar : UserUiEvent
}
