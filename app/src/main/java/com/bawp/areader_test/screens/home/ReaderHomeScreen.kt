package com.bawp.areader_test.screens.home

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.bawp.areader_test.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
        ) {


    Scaffold(
        topBar = {
                 ReaderAppBar(title = "A.Reader" , navController = navController )
//            ReaderAppBar(title = "A.Reader", ,
//                contentDescription = "Icon" )), navController = navController)

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
                HomeContent(navController = navController, viewModel, /*items = viewModel.data.value.data!!*/)


        }

    }
}

@Composable
fun HomeContent(
    navController: NavController,
    viewModel: HomeScreenViewModel,
               ) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    //Log.d("SER", "HomeContent: ${books.size}")
    if (!viewModel.data.value.data.isNullOrEmpty()) {
        //****Filter books by current, logged in user***
        listOfBooks = viewModel.data.value.data?.toList()!!.filter { book ->
              book.userId == currentUser?.uid.toString()
        }
    }
    //Log.d("SIZE", "HomeContent: ${viewModel.data.value.data?.size}")

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
       Row(modifier = Modifier.align(alignment = Alignment.Start)) {
           val currentUserName =
               if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) FirebaseAuth.getInstance().currentUser?.email.toString()
                   .split("@")[0] else {
                   "N/A"
               }

           TitleSection(label = "Your reading \n" + " activity right now...")
           Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.7f))
           Column {
               //Reference on how to use Icons in Compose: https://developer.android.com/reference/kotlin/androidx/compose/material/icons/Icons
               Icon(
                   Icons.Filled.AccountCircle,
                   contentDescription = null,
                   modifier = Modifier.clickable {
                       //Take user to stats screen
                       navController.navigate(ReaderScreens.ReaderStatsScreen.name)

                   }.size(45.dp),
                   tint = MaterialTheme.colors.secondaryVariant)
               Text(currentUserName,
                   modifier = Modifier.padding(2.dp),
                   style = MaterialTheme.typography.overline,
                   color = Color.Red,
                   fontSize = 15.sp,
                   maxLines = 1,
                   overflow = TextOverflow.Clip)
               Divider()
           }


       }
        ReadingRightNowArea(listOfBooks, navController, viewModel = viewModel)
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks, viewModel = viewModel, navController = navController)

    }

}

@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavController
                ) {

    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showProfile) {
                Image(painter = painterResource(id = R.drawable.ic_launcher_img),
                    contentDescription = "app icon",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
                        .scale(0.6f))



            }
            Text(text = title,
                color = Color.Red.copy(alpha = 0.7f),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))

            Spacer(modifier = Modifier.width(150.dp))
            if (showProfile) Row(horizontalArrangement = Arrangement.SpaceBetween) {



            } else Surface {}


        }
    }, actions = {
        IconButton(onClick = {

            //TODO: take user to stats Screen
            FirebaseAuth.getInstance().signOut().run {
                navController.navigate(ReaderScreens.LoginScreen.name)
            }


        }) {
            Image(painter = painterResource(id = R.drawable.ic_baseline_login_24),
                contentDescription = "logout",
                 modifier = Modifier.clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))))
//            Icon(imageVector = Icons.Filled.KeyboardArrowRight,
//                contentDescription = "Arrow Right",
//                modifier = Modifier.clickable {
//                    FirebaseAuth.getInstance().signOut().run {
//                        navController.navigate(ReaderScreens.LoginScreen.name)
//                    }
//
//                })


        }
    }, backgroundColor = Color.Transparent, elevation = 0.dp)


}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController, viewModel: HomeScreenViewModel) {
    //Filter by reading now books!
    val readingNowList = books.filter { book ->
         book.startedReading != null && book.finishedReading == null
    }
    HorizontalScrollableComponent(readingNowList) {
        //TODO: Let's pass an actual book object for simplicity!! NOPE!! Anti-pattern
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun BookListArea(books: List<MBook>,navController: NavController, viewModel: HomeScreenViewModel) {

    val addedBooks = books.filter { book ->
        book.startedReading == null && book.finishedReading == null
    }
    HorizontalScrollableComponent(addedBooks) {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")

    }
}

@Composable
fun FABContent(onTap: (String) -> Unit) {
    FloatingActionButton(onClick = { },

        shape = RoundedCornerShape(50), backgroundColor = Color(0xff92cbdf)) {
        IconButton(onClick = {
            onTap("")

        }) {
            Icon(imageVector = Icons.Filled.Add,
                contentDescription = "Add Book", tint = Color.White)

        }
    }
}