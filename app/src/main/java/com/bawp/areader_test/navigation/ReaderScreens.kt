package com.bawp.areader_test.navigation

enum class ReaderScreens(
    val label: String
                        ) {
    SplashScreen("Splash"),
    LoginScreen("Login"),
    CreateAccountScreen("Account"),
    Home("Home"),
    SearchScreen(label = "Search"),
    DetailScreen("Details");

    companion object {
        fun fromRoute(route: String?): ReaderScreens = when (route?.substringBefore("/")) {
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