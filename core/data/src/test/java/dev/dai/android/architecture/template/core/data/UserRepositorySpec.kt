package dev.dai.android.architecture.template.core.data

import dev.dai.android.architecture.core.data.user.DefaultUserRepository
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
import dev.dai.android.architecture.template.core.network.user.response.UserResponse
import dev.dai.android.architecture.template.core.network.user.response.fake
import dev.dai.android.architecture.template.core.testing.datasource.FakeUserNetworkDataSource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

class UserRepositorySpec : DescribeSpec({

  lateinit var userRepository: DefaultUserRepository

  val userNetworkDataSource = FakeUserNetworkDataSource()

  beforeSpec {
    userRepository = DefaultUserRepository(userNetworkDataSource)
  }

  describe("users") {
    context("has users") {
      userNetworkDataSource.setUsers(UserResponse.fake())
      val users = userRepository.users().first()

      it("should return a list of users") {
        users shouldBe listOf(User.fake())
      }
    }

    context("no users") {
      userNetworkDataSource.setUsers()
      val users = userRepository.users().first()

      it("should return an empty list") {
        users shouldBe emptyList()
      }
    }
  }

  describe("refresh") {
    userNetworkDataSource.setUsers()
    val usersInit = userRepository.users().first()

    userNetworkDataSource.setUsers(UserResponse.fake())
    userRepository.refresh()

    val usersRefreshed = userRepository.users().first()

    it("should refresh users") {
      usersInit shouldBe emptyList()
      usersRefreshed shouldBe listOf(User.fake())
    }
  }
})
