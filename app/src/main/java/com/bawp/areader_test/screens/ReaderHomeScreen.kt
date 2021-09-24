package com.bawp.areader_test.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bawp.areader_test.AppBar
import com.bawp.areader_test.navigation.ReaderScreens
import com.bawp.jetstate.readerapp.readerapp.HorizontalScrollableComponent
import com.bawp.jetstate.readerapp.readerapp.TitleSection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import androidx.compose.material.Surface

@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = {
            ReaderAppBar(title = "A.Reader", icon = Icons.Default.Edit)

        },//topBar
        floatingActionButton = {
            FABContent {
                Log.d("TAG", "FABContent: $it")
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        },

        ) {
        //content
        val currentUser = FirebaseAuth.getInstance().currentUser
        //Text(text = "Home ${currentUser?.email}")
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController = navController)
        }


    }
}

@Composable
fun HomeContent(navController: NavController) {
    var books = listOf("Gerry",
        "Cabrito",
        "Larisokco",
        "nevel",
        "carlos",
        "Pinto",
        "Louco",
        "Louco",
        "Louco")
    Column(Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        TitleSection(label = "Your reading \n" + " activity right now...")
        ReadingRightNowArea(books, navController)
        TitleSection(label = "Reading List")
        BookListArea(books)
    }

}

@Composable
fun ReaderAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
                ) {
    TopAppBar(title = {
        Row {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = "Logo",
                    tint = Color.Red.copy(alpha = 0.7f))
            }
            Text(text = title,
                color = Color.Red.copy(alpha = 0.7f),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
        }
    }, actions = {
        IconButton(onClick = {


        }) {
            if (showProfile) Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Icon(
                        Icons.Filled.AccountCircle, contentDescription = null,

                        )
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


@Composable
fun MainContent(
    allScreens: List<ReaderScreens>,
    onClickedItem: (ReaderScreens) -> Unit,
    currentScreen: ReaderScreens,
               ) {

    AppBar(title = "A.Reader", icon = Icons.Default.Edit)


}

@Composable
fun ReadingRightNowArea(books: List<String>, navController: NavController) {
    HorizontalScrollableComponent(books){
        navController.navigate(ReaderScreens.DetailScreen.name+"/$it")
        Log.d("Read", "ReadingRightNowArea: $it")
    }
}

@Composable
fun BookListArea(books: List<String>) {
    HorizontalScrollableComponent(books){}
}

@Composable
fun FABContent(onTap: (String) -> Unit) {
    FloatingActionButton(onClick = { },

        shape = RoundedCornerShape(50), backgroundColor = MaterialTheme.colors.secondary) {
        IconButton(onClick = {
            onTap("")
            //navigate to search scree
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book", tint = Color.Red)

        }

    }


}