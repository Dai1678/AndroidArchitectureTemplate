package dev.dai.android.architecture.template

import dev.dai.android.architecture.template.core.network.di.RetrofitFactory
import dev.dai.android.architecture.template.core.network.service.NetworkService
import dev.dai.android.architecture.template.core.network.user.DefaultUserNetworkDataSource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.mockserver.MockServerListener
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import dev.dai.android.architecture.template.util.TestResourceReader

class UserNetworkDataSourceSpec : DescribeSpec({
  listener(MockServerListener(1080))

  describe("UserApi") {
    MockServerClient("localhost", 1080).`when`(
      HttpRequest.request()
        .withMethod("GET")
        .withPath("/user/users.json")
    ).respond {
      HttpResponse.response()
        .withStatusCode(200)
        .withBody(TestResourceReader.readFileAsString("user/users.json"))
    }

    val dataSource = DefaultUserNetworkDataSource(
      networkService = NetworkService(),
      retrofit = RetrofitFactory.create(
        baseUrl = "http://localhost:1080",
        json = Json { ignoreUnknownKeys = true }
      )
    )
    val users = dataSource.getUsers()

    it("should return users") {
      users.size shouldBe 20
    }
  }
})
