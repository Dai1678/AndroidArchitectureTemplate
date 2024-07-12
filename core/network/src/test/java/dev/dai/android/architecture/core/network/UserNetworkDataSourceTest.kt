package dev.dai.android.architecture.core.network

import dev.dai.android.architecture.core.network.user.FakeUserNetworkDataSource
import dev.dai.android.architecture.core.network.user.UserNetworkDataSource
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
      listOf("Fake data 1", "Fake data 2"),
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
