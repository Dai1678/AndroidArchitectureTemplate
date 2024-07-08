package dev.dai.android.architecture.core.network

import dev.dai.android.architecture.core.network.feature1.FakeFeature1NetworkDataSource
import dev.dai.android.architecture.core.network.feature1.Feature1NetworkDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class Feature1NetworkDataSourceTest {

  private lateinit var dataSource: Feature1NetworkDataSource

  @Before
  fun setUp() {
    dataSource = FakeFeature1NetworkDataSource()
  }

  @Test
  fun testGetFeature1Data() = runTest {
    val data = dataSource.getFeature1Data()
    assertEquals(
      listOf("Fake data 1", "Fake data 2"),
      data
    )
  }

  @Test
  fun testGetFeature1DataError() = runTest {
    val dataSource = FakeFeature1NetworkDataSource()
    dataSource.setup(FakeFeature1NetworkDataSource.Status.Error)
    assertFails {
      dataSource.getFeature1Data()
    }
  }

}
