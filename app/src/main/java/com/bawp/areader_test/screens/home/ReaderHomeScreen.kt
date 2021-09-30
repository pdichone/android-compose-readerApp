package com.bawp.areader_test.screens.home

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

import com.bawp.areader_test.navigation.ReaderScreens
import com.bawp.areader_test.components.HorizontalScrollableComponent
import com.bawp.areader_test.components.TitleSection
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.bawp.areader_test.model.MBook

@Composable
fun Home(
    navController: NavController,
     viewModel: HomeScreenViewModel = hiltViewModel()
        ) {


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

        Surface(modifier = Modifier.fillMaxSize()) {

//            val bookInfo = produceState(initialValue = DataOrException<List<MBook>, Exception>()) {
//                value = viewModel.data.value
//            }.value

            HomeContent(navController = navController, items = viewModel.data.value.data!!)
        }


    }
}

@Composable
fun HomeContent(
    navController: NavController,
    items: List<MBook>
               ) {
    var listOfBooks = mutableListOf<MBook>()


    //Log.d("SER", "HomeContent: ${books.size}")
  if (!items.isNullOrEmpty()) {
      listOfBooks = items.toMutableList()
  }

    Log.d("SIZE", "HomeContent: ${listOfBooks.size}")

//    var bookss = listOf("Gerry",
//        "Cabrito",
//        "Larisokco",
//        "nevel",
//        "carlos",
//        "Pinto",
//        "Louco",
//        "Louco",
//        "Louco")
    Column(Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        TitleSection(label = "Your reading \n" + " activity right now...")
        ReadingRightNowArea(listOfBooks, navController)
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks)


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


            } else Surface {}

        }
    }, backgroundColor = Color.Transparent, elevation = 0.dp)

}




@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(books){
         //TODO: Go to UpdateBookScreen
        //navController.navigate(ReaderScreens.DetailScreen.name+"/$it")
        //Log.d("Read", "ReadingRightNowArea: $it")
        Log.d("Tapped", "ReadingRightNowArea: ${books[0].id}")
    }
}

@Composable
fun BookListArea(books: List<MBook>) {
    HorizontalScrollableComponent(books){}
}

@Composable
fun FABContent(onTap: (String) -> Unit) {
    FloatingActionButton(onClick = { },

        shape = RoundedCornerShape(50), backgroundColor = MaterialTheme.colors.secondary) {
        IconButton(onClick = {
            onTap("")

        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book", tint = Color.Red)

        }

    }


}