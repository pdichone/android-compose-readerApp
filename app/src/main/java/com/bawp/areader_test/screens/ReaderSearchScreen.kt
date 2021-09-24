package com.bawp.areader_test.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bawp.areader_test.data.BookRepository
import com.bawp.areader_test.model.ImageLinks
import com.bawp.areader_test.model.VolumeInfo
import com.bawp.areader_test.network.BooksApi
import com.bawp.areader_test.network.BooksViewModel
import com.bawp.areader_test.ui.utils.InputField
import java.util.*


@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchScreen(label: String = "search",
                 navController: NavController? = null ) {
    val context = LocalContext.current


    Scaffold(
        topBar = {
            AppBar(title = "Search books", icon = Icons.Default.ArrowBack, showProfile = false){
                navController?.popBackStack()
            }

        },)

    {
        Surface(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color.White) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                  ) {
                Row() {
                    SearchForm(loading = false) { query ->
                        val booksViewModel = BooksViewModel(query)
                        //Log.d("Query", "SearchScreen: $query")
                        Log.d("DATA", "SearchScreen: ${BooksViewModel(query).bookList}")

                        // booksViewModel() = BooksViewModel(query)

                       // Log.d("DATA", ": ${BooksViewModel(query).getAllBooks().toList()}")


                      //  val queue = Volley.newRequestQueue(context)
//                        val url = "https://www.googleapis.com/books/v1/volumes?q=$query"
//                        val jsonObjectRequest = JsonObjectRequest(
//                            Request.Method.GET, url,null,
//                            { response ->
//                               // val title = response.getString("title")
//                            val items = response.getJSONArray("items")
//                                for (i in 0 until items.length()) {
//                                    val item = items.getJSONObject(i)
//                                    val mainNode = item.getJSONObject("volumeInfo")
//                                    val volumeInfo = VolumeInfo(
//                                                 title = mainNode.getString("title"),
//                                                              // subtitle = mainNode.getString("subtitle"),
//                                                                //authors = (if(mainNode.getString("authors").isNullOrEmpty()) listOf("") else  mainNode.getString("authors")) as List<String>,
//                                                               authors = listOf(mainNode.getString("authors")),
//                                                               publisher = if (mainNode.getString("publisher").isNullOrEmpty()) mainNode.getString("publisher") else "N/A",
//                                                               publishedDate = (if (mainNode.getString("publishedDate").isNullOrEmpty()) "N/A"
//                                                               else mainNode.getString("publishedDate")),
//                                                               description = if (mainNode.getString("description").isNullOrEmpty()) "N/A" else mainNode.getString("description") ,
//                                                               categories = if (listOf(mainNode.getString("categories")).isNullOrEmpty() ) emptyList() else listOf(mainNode.getString("categories")),
//                                                               pageCount = mainNode.getInt("pageCount"),
//                                                               imageLinks =  ImageLinks(smallThumbnail = mainNode.getJSONObject("imageLinks").getString("smallThumbnail"),
//                                                                                      thumbnail = mainNode.getJSONObject("imageLinks").getString("thumbnail")),
//                                                               )
//
//                                    Log.d("Book", "SearchScreen: ${volumeInfo.authors} ${volumeInfo.categories}")
//
//                                    // Your code here
//                                }
//
//                            },
//                            { error ->
//                                //Log.d("Err", "SearchScreen: ${error.localizedMessage}")
//                                print(error.localizedMessage)
//                            })
//                        queue.add(jsonObjectRequest)
                    }


                }

            }


        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchForm(loading: Boolean = false,
               onDone: (String) -> Unit) {
    Column() {
        val searchQuery = rememberSaveable{ mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQuery.value) {
            searchQuery.value.trim().isNotEmpty()
        }
        InputField(
            valueState = searchQuery, labelId = "Android development" ,
            enabled = true, onAction = KeyboardActions {
                //The submit button is disabled unless the inputs are valid. wrap this in if statement to accomplish the same.
                if (!valid) return@KeyboardActions
                onDone(searchQuery.value.trim())
                searchQuery.value = ""
                keyboardController?.hide() //(to use this we need to use @ExperimentalComposeUiApi
            } )

    }

}

@Composable
fun AppBar(title: String, icon: ImageVector? = null, showProfile:Boolean = true, onBackArrowClicked: () -> Unit = {}) {
    TopAppBar(
        title = {
            Row {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = "Logo",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        })
                }
                val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
                //Spacer(modifier = Modifier.width())
                Text(text = title,
                    modifier = Modifier.padding(start = screenWidth.dp.times(0.08f),
                        bottom = 19.dp)
                    ,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
            }
        },
        actions = {
            IconButton(onClick = {}) {
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
                        modifier = Modifier.clickable {})

                } else Surface() {}


            }
        }, backgroundColor = Color.Transparent, elevation = 0.dp)

}

@Composable
fun searchBooks(query: String = "comments") {
    val queue = Volley.newRequestQueue(LocalContext.current)
    val url = "https://jsonplaceholder.typicode.com/posts"
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url,null,
        { response ->
            val title = response.getString("title")
            Log.d("Vol", "SearchBooks: $title")
        },
        { error ->
            Log.d("Error", "SearchBooks: ${error.localizedMessage}")
           // print(error.localizedMessage)
        })
    queue.add(jsonObjectRequest)

}