package com.bawp.areader_test.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bawp.areader_test.screens.search.BooksSearchViewModel
import com.bawp.areader_test.screens.*
import com.bawp.areader_test.screens.details.BookDetailsScreen
import com.bawp.areader_test.screens.home.Home
import com.bawp.areader_test.screens.home.HomeScreenViewModel
import com.bawp.areader_test.screens.login.ReaderLoginScreen
import com.bawp.areader_test.screens.search.SearchScreen
import com.bawp.areader_test.screens.stats.ReaderStatsScreen
import com.bawp.areader_test.screens.update.BookUpdateScreen


@ExperimentalComposeUiApi
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
  

    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name, ) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = homeViewModel)
        }


        composable(ReaderScreens.SearchScreen.name) {
            val mViewModel = hiltViewModel<BooksSearchViewModel>()
            SearchScreen(navController = navController, viewModel = mViewModel)
        }


        //to pass an actual object in navigation: https://www.youtube.com/watch?v=OgYfQNbl0ts&ab_channel=KiloLoco

//        composable(ReaderScreens.DetailScreen.name){
//            //val mViewModel = hiltViewModel<BooksListViewModel>()
//            BookDetailsScreen(navController = navController)
//        }
        val detailsName = ReaderScreens.DetailScreen.name
        composable("$detailsName/{bookId}", arguments = listOf(navArgument("bookId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{bookItemId}", arguments = listOf(navArgument("bookItemId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }

        }

        composable(ReaderScreens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = homeViewModel)
        }
    }
}

