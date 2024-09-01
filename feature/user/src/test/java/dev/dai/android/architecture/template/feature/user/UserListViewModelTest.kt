package dev.dai.android.architecture.template.feature.user

import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
import dev.dai.android.architecture.template.core.testing.MainDispatcherRule
import dev.dai.android.architecture.template.core.testing.repository.FakeUserRepository
import dev.dai.android.architecture.template.ui.UserMessageResult
import dev.dai.android.architecture.template.ui.UserMessageStateHolder
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals

class UserListViewModelTest {
  @get:Rule
  val dispatcherRule = MainDispatcherRule()

  @get:Rule
  val mockRule = MockKRule(this)

  private val userMessageStateHolder = mockk<UserMessageStateHolder>()

  @Test
  fun `Has users`() = runTest {
    val userRepository = FakeUserRepository()
    val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      viewModel.uiState.collect()
    }

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
      UserListContentUiState(
        userListUiState = UserListUiState.UserList(
          isRefresh = false,
          users = listOf(
            User.fake(),
            User.fake(
              id = 2,
              name = "User2",
              email = "user2@dev.dai.com",
              phone = "1234567890",
              website = "https://dev.dai.com",
            )
          )
        ),
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `Fetch users failed`() = runTest {
    val exception = TimeoutException()
    val userRepository = mockk<UserRepository> {
      coEvery { users() } returns flow { throw exception }
    }
    val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      viewModel.uiState.collect()
    }

    assertEquals(
      UserListContentUiState(
        userListUiState = UserListUiState.UserList(
          isRefresh = false,
          users = emptyList()
        )
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `Refresh users`() = runTest {
    val userRepository = FakeUserRepository()
    val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      viewModel.uiState.collect()
    }

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

    viewModel.refresh()

    assertEquals(
      UserListContentUiState(
        userListUiState = UserListUiState.UserList(
          isRefresh = false,
          users = listOf(User.fake())
        )
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun `Refresh users failed`() = runTest {
    val exception = TimeoutException()
    val userRepository = mockk<UserRepository> {
      coEvery { users() } returns flowOf(listOf(User.fake()))
      coEvery { refresh() } throws exception
    }
    coEvery { userMessageStateHolder.showMessage(exception.message.orEmpty()) } returns UserMessageResult.Dismissed
    val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      viewModel.uiState.collect()
    }

    viewModel.refresh()

    assertEquals(
      UserListContentUiState(
        userListUiState = UserListUiState.UserList(
          isRefresh = false,
          users = listOf(User.fake())
        )
      ),
      viewModel.uiState.value
    )

    coVerify(exactly = 1) { userMessageStateHolder.showMessage(exception.message.orEmpty()) }
  }
}
