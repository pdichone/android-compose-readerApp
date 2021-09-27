package com.bawp.areader_test.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bawp.areader_test.data.BooksListViewModel
import com.bawp.areader_test.screens.*
import com.bawp.areader_test.screens.details.BookDetailViewModel
import com.bawp.areader_test.screens.details.BookDetailsScreen
import com.bawp.areader_test.screens.login.ReaderLoginScreen


@ExperimentalComposeUiApi
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
  

    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){
        composable(ReaderScreens.SplashScreen.name, ){
             ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){
            Home(navController = navController)
        }


        composable(ReaderScreens.SearchScreen.name){
            val mViewModel = hiltViewModel<BooksListViewModel>()
            SearchScreen(navController = navController,  viewModel = mViewModel)
        }

        val detailsName = ReaderScreens.DetailScreen.name
        //to pass an actual object in navigation: https://www.youtube.com/watch?v=OgYfQNbl0ts&ab_channel=KiloLoco

//        composable(detailsName){
//            //val mViewModel = hiltViewModel<BooksListViewModel>()
//            BookDetailsScreen(navController = navController)
//        }
//        composable(
//            "$detailsName/{title}",
//                  arguments = listOf(navArgument("title"){
//                      type = NavType.StringType
//                  })){ backStackEntry ->
//             backStackEntry.arguments?.getString("title").let {
//                 BookDetailsScreen(navController = navController, text = it.toString())
//             }

        composable(
            "$detailsName/{bookId}",
            arguments = listOf(navArgument("bookId"){
                type = NavType.StringType
            })){ backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                val viewModel = hiltViewModel<BookDetailViewModel>()
                BookDetailsScreen(navController = navController, bookId = it.toString(),
                                 viewModel)
            }

        }

    }
}

