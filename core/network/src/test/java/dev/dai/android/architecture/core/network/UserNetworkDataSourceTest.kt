package dev.dai.android.architecture.core.network

import dev.dai.android.architecture.core.network.user.FakeUserNetworkDataSource
import dev.dai.android.architecture.core.network.user.UserNetworkDataSource
import dev.dai.android.architecture.core.network.user.response.UserResponse
import dev.dai.android.architecture.core.network.user.response.fake
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class UserNetworkDataSourceTest {

  private lateinit var dataSource: UserNetworkDataSource

  @Before
  fun setUp() {
    dataSource = FakeUserNetworkDataSource()
  }

  @Test
  fun testGetUsers() = runTest {
    val data = dataSource.getUsers()
    assertEquals(
      listOf(
        UserResponse.fake(),
        UserResponse.fake(
          id = 2,
          name = "User2",
          email = "user2@dev.dai.com",
          phone = "1234567890",
          website = "https://dev.dai.com"
        )
      ),
      data
    )
  }

  @Test
  fun testGetUsersError() = runTest {
    val dataSource = FakeUserNetworkDataSource()
    dataSource.setup(FakeUserNetworkDataSource.Status.Error)
    assertFails {
      dataSource.getUsers()
    }
  }

}
