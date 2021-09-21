package com.bawp.areader_test.navigation

import android.window.SplashScreen

enum class ReadScreen(
    val label: String
                     ) {
    SplashScreen("Splash"),
    LoginScreen("Login"),
    CreateAccountScreen("Account"),
    Home("Home"),
    SearchScreen(label = "Search"),
    DetailScreen("Details");

    companion object {
        fun fromRoute(route: String?): ReadScreen = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            Home.name -> Home
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            null -> Home
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }


}