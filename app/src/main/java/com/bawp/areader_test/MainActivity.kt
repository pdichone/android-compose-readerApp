package com.bawp.areader_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bawp.areader_test.navigation.ReaderNavigation
import com.bawp.areader_test.ui.theme.AReaderTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AReaderTestTheme {
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
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AReaderTestTheme {
        Greeting("Android")
    }
}