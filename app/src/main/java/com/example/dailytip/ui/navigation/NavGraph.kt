package com.example.dailytip.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailytip.ui.home.HomeScreen
import com.example.dailytip.ui.tiplist.TipListScreen
import com.example.dailytip.ui.viewmodel.TipViewModel

object Routes {
    const val HOME = "home"
    const val TIP_LIST = "tip_list"
}

@Composable
fun DailyTipNavGraph(
    navController: NavHostController,
    viewModel: TipViewModel
) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToList = { navController.navigate(Routes.TIP_LIST) }
            )
        }
        composable(Routes.TIP_LIST) {
            TipListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
