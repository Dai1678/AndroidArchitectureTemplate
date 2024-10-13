package dev.dai.android.architecture.template.core.data

import dev.dai.android.architecture.core.data.repository.DefaultUserRepository
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
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
    val users = userRepository.users().first()
    it("should return a list of users") {
      users shouldBe listOf(
        User.fake(),
        User.fake(
          id = 2,
          name = "User2",
          email = "user2@dev.dai.com",
          phone = "1234567890",
          website = "https://dev.dai.com",
        )
      )
    }
  }
})
