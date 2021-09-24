package com.bawp.areader_test.navigation

import androidx.compose.ui.input.key.Key.Companion.Home

enum class ReaderScreens(
    val label: String
                        ) {
    SplashScreen("Splash"),
    LoginScreen("Login"),
    CreateAccountScreen("Account"),
    ReaderHomeScreen("Home"),
    SearchScreen(label = "Search"),
    DetailScreen("Details");

    companion object {
        fun fromRoute(route: String?): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }


}