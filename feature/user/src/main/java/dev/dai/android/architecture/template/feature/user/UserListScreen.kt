package dev.dai.android.architecture.template.feature.user

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.dai.android.architecture.template.core.model.User
import dev.dai.android.architecture.template.core.model.fake
import dev.dai.android.architecture.template.designsystem.theme.AndroidArchitectureTemplateTheme
import dev.dai.android.architecture.template.ui.SnackbarMessageEffect

const val USER_LIST_SCREEN_ROUTE = "user_list"
fun NavGraphBuilder.userListScreen() {
  composable(route = USER_LIST_SCREEN_ROUTE) {
    UserListScreen()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
  modifier: Modifier = Modifier,
  viewModel: UserListViewModel = hiltViewModel(),
) {
  val uiState: UserListContentUiState by viewModel.uiState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }
  val pullToRefreshState = rememberPullToRefreshState()

  if (pullToRefreshState.isRefreshing) {
    LaunchedEffect(Unit) {
      viewModel.refresh()
    }
  }

  SnackbarMessageEffect(
    snackbarHostState = snackbarHostState,
    userMessageStateHolder = viewModel.userMessageStateHolder
  )

  UserListContent(
    uiState = uiState,
    snackbarHostState = snackbarHostState,
    pullToRefreshState = pullToRefreshState,
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
  snackbarHostState: SnackbarHostState,
  pullToRefreshState: PullToRefreshState,
  modifier: Modifier = Modifier,
) {
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  Scaffold(
    modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    topBar = {
      LargeTopAppBar(
        title = { Text(text = "ユーザー") },
        scrollBehavior = scrollBehavior,
      )
    }
  ) { innerPadding ->
    UserList(
      uiState = uiState.userListUiState,
      pullToRefreshState = pullToRefreshState,
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
    val isRefresh: Boolean,
    val users: List<User>,
  ) : UserListUiState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserList(
  uiState: UserListUiState,
  pullToRefreshState: PullToRefreshState,
  modifier: Modifier = Modifier,
  onUserClick: (userId: Int) -> Unit,
) {
  Box(modifier = modifier.fillMaxSize()) {
    when (uiState) {
      UserListUiState.Loading -> {
        LaunchedEffect(Unit) {
          pullToRefreshState.startRefresh()
        }

        PullToRefreshContainer(
          state = pullToRefreshState,
          modifier = Modifier.align(Alignment.TopCenter)
        )
      }

      is UserListUiState.UserList -> {
        LaunchedEffect(uiState.isRefresh) {
          if (uiState.isRefresh) {
            pullToRefreshState.startRefresh()
          } else {
            pullToRefreshState.endRefresh()
          }
        }

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
        PullToRefreshContainer(
          state = pullToRefreshState,
          modifier = Modifier.align(Alignment.TopCenter)
        )
      }
    }
  }
}

private const val PREVIEW_USER_LIST_SIZE = 20

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun UserListContentPreview() {
  AndroidArchitectureTemplateTheme {
    Surface {
      UserListContent(
        uiState = UserListContentUiState(
          userListUiState = UserListUiState.UserList(
            isRefresh = false,
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
        snackbarHostState = SnackbarHostState(),
        pullToRefreshState = rememberPullToRefreshState(),
      )
    }
  }
}
