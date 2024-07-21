package dev.dai.android.architecture.feature.user

import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.model.fake
import dev.dai.android.architecture.core.test.MainDispatcherRule
import dev.dai.android.architecture.core.test.repository.FakeUserRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class UserListViewModelTest {
  @get:Rule
  val dispatcherRule = MainDispatcherRule()

  private val userRepository = FakeUserRepository()

  private lateinit var viewModel: UserListViewModel

  @Before
  fun setup() {
    viewModel = UserListViewModel(userRepository)
  }

  @Test
  fun `state has users`() = runTest {
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      viewModel.uiState.collect()
    }

    assertEquals(
      UserUiState.NoUsers(isLoading = true, event = null),
      viewModel.uiState.value
    )

    userRepository.emitUser(
      listOf(
        User.fake(),
        User.fake(
          id = 2,
          name = "User2",
          email = "user2@dev.dai.com",
          phone = "1234567890",
          website = "https://dev.dai.com",
        )
      )
    )

    assertEquals(
      UserUiState.HasUsers(
        isLoading = false,
        users = listOf(
          User.fake(),
          User.fake(
            id = 2,
            name = "User2",
            email = "user2@dev.dai.com",
            phone = "1234567890",
            website = "https://dev.dai.com",
          )
        ),
        event = null
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `fetch users failed`() = runTest {
    userRepository.setup(isError = true)
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      viewModel.uiState.collect()
    }

    assertEquals(
      UserUiState.NoUsers(isLoading = true, event = null),
      viewModel.uiState.value
    )

    userRepository.emitUser(
      listOf(
        User.fake(),
        User.fake(
          id = 2,
          name = "User2",
          email = "user2@dev.dai.com",
          phone = "1234567890",
          website = "https://dev.dai.com",
        )
      )
    )

    assertFails {
      userRepository.users.first()
    }
    assertTrue(viewModel.uiState.value is UserUiState.EmptyError)
  }
}
