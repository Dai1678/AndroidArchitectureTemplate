package dev.dai.android.architecture.template.feature.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
import dev.dai.android.architecture.template.designsystem.component.LoadingContent
import dev.dai.android.architecture.template.designsystem.theme.MyTheme
import dev.dai.android.architecture.template.ui.SnackbarMessageEffect

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
  val snackBarHostState = remember { SnackbarHostState() }

  SnackbarMessageEffect(
    snackbarHostState = snackBarHostState,
    userMessageStateHolder = viewModel.userMessageStateHolder
  )

  UserListContent(
    uiState = uiState,
    snackBarHostState = snackBarHostState,
    modifier = modifier,
    onRefresh = viewModel::refresh
  )
}

@Stable
internal data class UserListContentUiState(
  val userListUiState: UserListUiState,
)

@Stable
internal sealed interface UserListUiState {
  data object Loading : UserListUiState
  data class UserList(
    val isRefreshUserList: Boolean,
    val users: List<User>,
  ) : UserListUiState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListContent(
  uiState: UserListContentUiState,
  snackBarHostState: SnackbarHostState,
  modifier: Modifier = Modifier,
  onRefresh: () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  val pullToRefreshState = rememberPullToRefreshState()

  Scaffold(
    snackbarHost = { SnackbarHost(snackBarHostState) },
    topBar = {
      LargeTopAppBar(
        title = { Text(text = "ユーザー") },
        scrollBehavior = scrollBehavior,
      )
    }
  ) { innerPadding ->
    when (uiState.userListUiState) {
      UserListUiState.Loading -> {
        LoadingContent()
      }

      is UserListUiState.UserList -> {
        PullToRefreshBox(
          modifier = Modifier.padding(innerPadding),
          state = pullToRefreshState,
          isRefreshing = uiState.userListUiState.isRefreshUserList,
          onRefresh = onRefresh
        ) {
          UserList(
            uiState = uiState.userListUiState,
            modifier = modifier
              .fillMaxSize()
              .nestedScroll(scrollBehavior.nestedScrollConnection),
            onUserClick = {}
          )
        }
      }
    }
  }
}

@Composable
private fun UserList(
  uiState: UserListUiState.UserList,
  modifier: Modifier = Modifier,
  onUserClick: (userId: Int) -> Unit,
) {
  LazyColumn(
    modifier = modifier.fillMaxSize()
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

private const val PREVIEW_USER_LIST_SIZE = 20

@PreviewLightDark
@Composable
private fun UserListContentPreview() {
  MyTheme {
    Surface {
      UserListContent(
        uiState = UserListContentUiState(
          userListUiState = UserListUiState.UserList(
            isRefreshUserList = false,
            users = buildList {
              repeat(PREVIEW_USER_LIST_SIZE) {
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
        ),
        snackBarHostState = SnackbarHostState(),
        onRefresh = {}
      )
    }
  }
}
