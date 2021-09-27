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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bawp.areader_test.navigation.ReaderNavigation
import com.bawp.areader_test.navigation.ReaderScreens
import com.bawp.areader_test.ui.theme.AReaderTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AReaderTestTheme {
                val allScreens = ReaderScreens.values().toList()
                val navController = rememberNavController()
                val backstackEntry = navController.currentBackStackEntryAsState()
                val currentScreen = ReaderScreens.fromRoute(backstackEntry.value?.destination?.route)

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background,
                       modifier = Modifier.fillMaxSize()) {

                    ReaderApp()

                }
            }
        }
    }
}

@Composable
fun AppBar(title: String, icon: ImageVector? = null, showProfile:Boolean = true) {
    TopAppBar(
        title = {
            Row {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = "Logo", tint = Color.Red.copy(alpha = 0.7f))
                }
                Text(text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
            }
        },
        actions = {
            IconButton(onClick = {
            }) {
                if (showProfile) Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Icon(
                            Icons.Filled.AccountCircle, contentDescription = null, )
                        Text("P.D",
                            modifier = Modifier.padding(2.dp),
                            style = MaterialTheme.typography.overline,
                            color = Color.Red)
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Arrow Right",
                        modifier = Modifier.clickable {


                        })


                } else Surface() {}


            }
        }, backgroundColor = Color.Transparent, elevation = 0.dp)

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
    AReaderTestTheme {
       // Greeting("Android")
    }
}