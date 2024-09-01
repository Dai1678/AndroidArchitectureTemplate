package dev.dai.android.architecture.template.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.dai.android.architecture.template.feature.user.USER_LIST_SCREEN_ROUTE
import dev.dai.android.architecture.template.feature.user.userListScreen

@Composable
fun AppNavHost(
  navController: NavHostController = rememberNavController(),
  startDestination: String = USER_LIST_SCREEN_ROUTE,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination
  ) {
    userListScreen()
  }
}
