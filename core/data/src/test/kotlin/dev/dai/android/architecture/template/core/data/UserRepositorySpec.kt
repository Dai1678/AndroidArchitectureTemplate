package dev.dai.android.architecture.template.core.data

import dev.dai.android.architecture.template.core.data.user.DefaultUserRepository
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
import dev.dai.android.architecture.template.core.network.user.fake.FakeUserNetworkDataSource
import dev.dai.android.architecture.template.core.network.user.response.UserResponse
import dev.dai.android.architecture.template.core.network.user.response.fake
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

class UserRepositorySpec : DescribeSpec({

  lateinit var userRepository: DefaultUserRepository

  val userNetworkDataSource = FakeUserNetworkDataSource()

  beforeSpec {
    userRepository = DefaultUserRepository(userNetworkDataSource)
  }

  describe("usersStream") {
    context("has users") {
      userNetworkDataSource.setUsers(UserResponse.fake())
      val users = userRepository.usersStream().first()

      it("should return a list of users") {
        users shouldBe listOf(User.fake())
      }
    }

    context("no users") {
      userNetworkDataSource.setUsers()
      val users = userRepository.usersStream().first()

      it("should return an empty list") {
        users shouldBe emptyList()
      }
    }
  }

  describe("refresh") {
    userNetworkDataSource.setUsers()
    val usersInit = userRepository.usersStream().first()

    userNetworkDataSource.setUsers(UserResponse.fake())
    userRepository.refresh()

    val usersRefreshed = userRepository.usersStream().first()

    it("should refresh users") {
      usersInit shouldBe emptyList()
      usersRefreshed shouldBe listOf(User.fake())
    }
  }
})
