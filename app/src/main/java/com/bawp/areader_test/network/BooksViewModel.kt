package com.bawp.areader_test.network

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bawp.areader_test.data.BookRepository
import com.bawp.areader_test.model.Book
import com.bawp.areader_test.model.Item
import kotlinx.coroutines.launch
import org.json.JSONObject

class BooksViewModel(query:String): ViewModel(){
    private val searchQuery = query

    private lateinit var repository: BookRepository
    private val api: GoogleBooksApiService = BooksApi.retrofitService

     var bookList: List<Item> by mutableStateOf(listOf())


    init {
        //getStuff()
        fetchBooks()

    }

      fun fetchBooks() {
         repository = BookRepository(api)
         viewModelScope.launch {
              try {
                  var response = repository.fetchBooks(searchQuery)
                  when (response) {
                      is BookRepository.Result.Success -> {
                          Log.d("MainViewModel", "fetchBooks: Success")
                          bookList = response.itemList
                      }
                      is BookRepository.Result.Failure -> {
                          Log.d("TAG", "fetchBooks: Failure")
                      }
                      else -> {}
                  }

              }catch (e: Exception){
                  Log.e("ViewModel:Fetch Books", "ViewModelfetchBooks: ${e.localizedMessage}", )}
         }
    }


}