package com.bawp.areader_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bawp.areader_test.navigation.ReaderNavigation
import com.bawp.areader_test.navigation.ReaderScreens
import com.bawp.areader_test.ui.theme.AReaderTestTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /*
      Tip 1: Add android:windowSoftInputMode="adjustResize"  in the activity in manifest file
      so that when keyboard pops up, the screen adjusts itself to accommodate
      the keyboard

     TODO: Tip 2: Need to make this app darkmode compatible. Tested on Bonni's phone,
      which is set to darkmode, and the colors didn't work :(
     */
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AReaderTestTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = Color(0xff92cbdf).copy(alpha = 0.4f))
                //systemUiController.setNavigationBarColor(Color.LightGray,darkIcons = true)

                val allScreens = ReaderScreens.values().toList()
                val navController = rememberNavController()
                val backstackEntry = navController.currentBackStackEntryAsState()
                val currentScreen = ReaderScreens.fromRoute(backstackEntry.value?.destination?.route)

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background,
                       modifier = Modifier.fillMaxSize()
                           .padding(top = 46.dp)) {
                    ReaderApp()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ReaderApp() {
     Surface( modifier = Modifier
         .fillMaxSize()
         .padding(10.dp)) {
         Column(verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {
             ReaderNavigation()
         }

     }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AReaderTestTheme() {
       // Greeting("Android")
    }
}