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
    DetailScreen("Details"),
    UpdateScreen("Update"),
    ReaderStatsScreen("Stats");



    companion object {
        fun fromRoute(route: String?): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }


}