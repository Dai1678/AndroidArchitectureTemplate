package dev.dai.android.architecture.feature.user

import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.model.fake
import dev.dai.android.architecture.core.test.MainDispatcherRule
import dev.dai.android.architecture.core.test.repository.FakeUserRepository
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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

  @Test
  fun `Has users`() = runTest {
    val userRepository = FakeUserRepository()
    val viewModel = UserListViewModel(userRepository)
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
      coEvery { users } returns flow { throw exception }
    }
    val viewModel = UserListViewModel(userRepository)
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      viewModel.uiState.collect()
    }

    assertEquals(
      UserListContentUiState(
        userListUiState = UserListUiState.UserList(
          users = emptyList()
        )
      ),
      viewModel.uiState.value
    )
  }
}
