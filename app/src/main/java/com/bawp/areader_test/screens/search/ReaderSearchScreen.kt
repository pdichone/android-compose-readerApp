package com.bawp.areader_test.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.bawp.areader_test.components.ReaderAppBar
import com.bawp.areader_test.model.Item
import com.bawp.areader_test.navigation.ReaderScreens
import com.bawp.areader_test.utils.InputField
import java.util.*


@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    label: String = "search",
    navController: NavController,
    viewModel: BooksSearchViewModel = hiltViewModel(),
                ) {

    Scaffold(
        topBar = {
            ReaderAppBar(title = "Search books", icon = Icons.Default.ArrowBack, showProfile = false){
                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
            }

        },)

    {
        Surface() {

            Column {

                SearchForm(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){
                    viewModel.searchBooks(it)
                }

//            SearchBar(hint = "Search...", modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)) {
//                // here do the api search
//                viewModel.searchBooks(it)
//
//            }
                Spacer(modifier = Modifier.height(16.dp))
                //Log.d("TAG", "SearchScreen: ${viewModel.bookList}")

                BookList(navController = navController)
            }
        }
    }
}
@ExperimentalComposeUiApi
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}) {
    Column() {
        val searchQuery = rememberSaveable{ mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQuery.value) {
            searchQuery.value.trim().isNotEmpty()
        }
        InputField(
//            modifier.fillMaxWidth()
//            .shadow(5.dp, CircleShape)
//            .background(Color.White, CircleShape)
//            .padding(horizontal = 20.dp, vertical = 12.dp),
            valueState = searchQuery, labelId = "Search" ,
            enabled = true, onAction = KeyboardActions {
                //The submit button is disabled unless the inputs are valid. wrap this in if statement to accomplish the same.
                if (!valid) return@KeyboardActions
                onSearch(searchQuery.value.trim())
                searchQuery.value = ""
                keyboardController?.hide() //(to use this we need to use @ExperimentalComposeUiApi
            } )

    }

}

@Composable
fun BookList(
    navController: NavController,
    viewModel: BooksSearchViewModel = hiltViewModel(),
            ) {
//    val bookList = remember {
//        viewModel.list
//    }

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        contentPadding = PaddingValues(16.dp)) {

        items(items = viewModel.list) { item ->
            BookRow(book = item, navController = navController)


        }
    }

}

@Composable
fun BookRow(
    book: Item,
    navController: NavController,
           ) {

    Card(modifier = Modifier
        .clickable {
            //go to details screen and show more about the book
            navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 6.dp){

        Row( modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top) {
            var imageUrl = ""
//            val photoUrl = remember {
//                mutableStateOf(value = book.volumeInfo.imageLinks!!.smallThumbnail)
//            }
            imageUrl = if(book.volumeInfo.imageLinks?.smallThumbnail?.isEmpty() == true){
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"

            }else {
                book.volumeInfo.imageLinks?.smallThumbnail.toString()
            }

            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
                 )

            Column() {

                Text("${book.volumeInfo.title}",
                    overflow = TextOverflow.Ellipsis)
                Text(" Author: ${book.volumeInfo.authors?.get(0)}", softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption)

                Text("Date: ${book.volumeInfo.publishedDate}", softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption)
                Text(book.volumeInfo.categories.toString(), softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption)

            }
        }
    }
}

//@ExperimentalComposeUiApi
//@Composable
//fun SearchForm(loading: Boolean = false,
//               onDone: (String) -> Unit) {
//    Column() {
//        val searchQuery = rememberSaveable{ mutableStateOf("") }
//        val keyboardController = LocalSoftwareKeyboardController.current
//        val valid = remember(searchQuery.value) {
//            searchQuery.value.trim().isNotEmpty()
//        }
//        InputField(
//            valueState = searchQuery, labelId = "Android development" ,
//            enabled = true, onAction = KeyboardActions {
//                //The submit button is disabled unless the inputs are valid. wrap this in if statement to accomplish the same.
//                if (!valid) return@KeyboardActions
//                onDone(searchQuery.value.trim())
//                searchQuery.value = ""
//                keyboardController?.hide() //(to use this we need to use @ExperimentalComposeUiApi
//            } )
//
//    }
//
//}


