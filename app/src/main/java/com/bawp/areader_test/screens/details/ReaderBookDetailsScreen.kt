package com.bawp.areader_test.screens.details

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bawp.areader_test.repository.BookRepository

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: BookDetailViewModel = hiltViewModel(),
                     ) {
    Surface() {


//        val currentBook = viewModel.bookList.first { book ->
//             bookId == book.id
//
//        }
        //Text(text = currentBook.volumeInfo.title.toString() )

        val bookInfo = produceState(initialValue = BookRepository.Result.LOADING){
                value = viewModel.getBookInfo(bookId) as BookRepository.Result.LOADING
        }
       // Log.d("TAG", "BookDetailsScreen: ${bookInfo.value.toString()}")

       // Text(text = bookInfo.value )

    }
}