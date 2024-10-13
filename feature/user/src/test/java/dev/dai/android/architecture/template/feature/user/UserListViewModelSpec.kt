package dev.dai.android.architecture.template.feature.user

import dev.dai.android.architecture.core.data.repository.UserRepository
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
import dev.dai.android.architecture.template.core.testing.MainDispatcherListener
import dev.dai.android.architecture.template.core.testing.repository.FakeUserRepository
import dev.dai.android.architecture.template.ui.UserMessageResult
import dev.dai.android.architecture.template.ui.UserMessageStateHolder
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.util.concurrent.TimeoutException

class UserListViewModelSpec : DescribeSpec({
  extension(MainDispatcherListener())

  val userMessageStateHolder = mockk<UserMessageStateHolder>()

  describe("init UiState") {
    context("has users") {
      val userRepository = FakeUserRepository()
      val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
      val job = launch(UnconfinedTestDispatcher()) {
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

      job.cancel()

      it("uiState should be users") {
        viewModel.uiState.value shouldBe UserListContentUiState(
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
          )
        )
      }
    }

    context("fetch users failed") {
      val exception = TimeoutException()
      val userRepository = mockk<UserRepository> {
        coEvery { users() } returns flow { throw exception }
      }
      val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
      val job = launch(UnconfinedTestDispatcher()) {
        viewModel.uiState.collect()
      }
      job.cancel()

      it("uiState should be empty") {
        viewModel.uiState.value shouldBe UserListContentUiState(
          userListUiState = UserListUiState.UserList(
            isRefresh = false,
            users = emptyList()
          )
        )
      }
    }
  }

  describe("refresh users") {
    context("refresh success") {
      val userRepository = FakeUserRepository()
      val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
      val job = launch(UnconfinedTestDispatcher()) {
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

      job.cancel()

      it("uiState should be users") {
        viewModel.uiState.value shouldBe UserListContentUiState(
          userListUiState = UserListUiState.UserList(
            isRefresh = false,
            users = listOf(User.fake())
          )
        )
      }
    }

    context("refresh failed") {
      val exception = TimeoutException()
      val userRepository = mockk<UserRepository> {
        coEvery { users() } returns flowOf(listOf(User.fake()))
        coEvery { refresh() } throws exception
      }
      coEvery { userMessageStateHolder.showMessage(exception.message.orEmpty()) } returns UserMessageResult.Dismissed
      val viewModel = UserListViewModel(userMessageStateHolder, userRepository)
      val job = launch(UnconfinedTestDispatcher()) {
        viewModel.uiState.collect()
      }

      viewModel.refresh()

      job.cancel()

      it("uiState should be users") {
        viewModel.uiState.value shouldBe UserListContentUiState(
          userListUiState = UserListUiState.UserList(
            isRefresh = false,
            users = listOf(User.fake())
          )
        )
      }

      coVerify(exactly = 1) { userMessageStateHolder.showMessage(exception.message.orEmpty()) }
    }
  }
})
