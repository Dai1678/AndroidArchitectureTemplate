package dev.dai.android.architecture.core.data

import dev.dai.android.architecture.core.data.repository.DefaultUserRepository
import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.model.fake
import dev.dai.android.architecture.core.test.datasource.FakeUserNetworkDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserRepositoryTest {

  private lateinit var userRepository: DefaultUserRepository

  private val userNetworkDataSource = FakeUserNetworkDataSource()

  @Before
  fun setup() {
    userRepository = DefaultUserRepository(userNetworkDataSource)
  }

  @Test
  fun `users should return a list of users`() = runTest {
    val users = userRepository.users().first()
    assertEquals(
      listOf(
        User.fake(),
        User.fake(
          id = 2,
          name = "User2",
          email = "user2@dev.dai.com",
          phone = "1234567890",
          website = "https://dev.dai.com",
        )
      ),
      users
    )
  }
}
