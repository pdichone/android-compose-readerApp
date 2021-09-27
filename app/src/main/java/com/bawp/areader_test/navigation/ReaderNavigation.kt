package com.bawp.areader_test.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bawp.areader_test.network.BooksViewModel
import com.bawp.areader_test.screens.*
<<<<<<< HEAD
import com.bawp.areader_test.screens.details.BookDetailViewModel
import com.bawp.areader_test.screens.details.BookDetailsScreen
import com.bawp.areader_test.screens.login.ReaderLoginScreen
=======
>>>>>>> parent of 983f2eb (Hilt, repository and Viewmodel setup)


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
            SearchScreen(navController = navController)
        }

        val detailsName = ReaderScreens.DetailScreen.name
        //to pass an actual object in navigation: https://www.youtube.com/watch?v=OgYfQNbl0ts&ab_channel=KiloLoco

<<<<<<< HEAD
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
=======
        composable(
            "$detailsName/{title}",
                  arguments = listOf(navArgument("title"){
                      type = NavType.StringType
                  })){ backStackEntry ->
             backStackEntry.arguments?.getString("title").let {
                 BookDetailsScreen(navController = navController, text = it.toString())
             }
>>>>>>> parent of 983f2eb (Hilt, repository and Viewmodel setup)

        }

    }
}

