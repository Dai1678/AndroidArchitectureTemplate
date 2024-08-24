package dev.dai.android.architecture.feature.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.dai.android.architecture.core.model.User
import dev.dai.android.architecture.core.model.fake
import dev.dai.android.architecture.designsystem.theme.AndroidArchitectureTemplateTheme

const val USER_LIST_SCREEN_ROUTE = "user_list"
fun NavGraphBuilder.userListScreen() {
  composable(route = USER_LIST_SCREEN_ROUTE) {
    UserListScreen()
  }
}

@Composable
fun UserListScreen(
  modifier: Modifier = Modifier,
  viewModel: UserListViewModel = hiltViewModel(),
) {
  val uiState: UserListContentUiState by viewModel.uiState.collectAsStateWithLifecycle()

  UserListContent(
    uiState = uiState,
    modifier = modifier,
  )
}

@Stable
internal data class UserListContentUiState(
  val userListUiState: UserListUiState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListContent(
  uiState: UserListContentUiState,
  modifier: Modifier = Modifier,
) {
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  Scaffold(
    topBar = {
      LargeTopAppBar(
        title = { Text(text = "ユーザー") },
        scrollBehavior = scrollBehavior,
      )
    }
  ) { innerPadding ->
    UserList(
      uiState = uiState.userListUiState,
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .nestedScroll(scrollBehavior.nestedScrollConnection),
      onUserClick = {}
    )
  }
}

@Stable
internal sealed interface UserListUiState {
  data object Loading : UserListUiState
  data class UserList(
    val users: List<User>,
  ) : UserListUiState
}

@Composable
private fun UserList(
  uiState: UserListUiState,
  modifier: Modifier = Modifier,
  onUserClick: (userId: Int) -> Unit,
) {
  Box(modifier = modifier.fillMaxSize()) {
    when (uiState) {
      UserListUiState.Loading -> {
        // TODO() LoadingContent
      }

      is UserListUiState.UserList -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize()
        ) {
          items(uiState.users) { user ->
            ListItem(
              headlineContent = {
                Text(text = user.name)
              },
              supportingContent = {
                Text(text = user.email)
              },
              modifier = Modifier.clickable {
                onUserClick(user.id)
              }
            )
          }
        }
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun UserListContentPreview() {
  AndroidArchitectureTemplateTheme {
    Surface {
      UserListContent(
        uiState = UserListContentUiState(
          userListUiState = UserListUiState.UserList(
            users = buildList {
              val userListSize = 20
              repeat(userListSize) {
                User.fake(
                  id = it,
                  name = "User$it",
                  email = "user$it@dev.dai.com",
                  phone = "1234567890",
                  website = "https://dev.dai.com",
                ).let { user -> add(user) }
              }
            }
          )
        )
      )
    }
  }
}
