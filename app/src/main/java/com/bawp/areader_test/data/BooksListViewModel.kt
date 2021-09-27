package com.bawp.areader_test.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bawp.areader_test.model.Book
import com.bawp.areader_test.model.Item
import com.bawp.areader_test.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksListViewModel @Inject constructor(
    private val repository: BookRepository
                                            ): ViewModel() {

    var bookList: List<Item> by mutableStateOf(listOf())

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }
    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                //do nothing
                return@launch
            }
            try {
                when(val response = repository.getBooks(query)) {
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
                Log.d("Exce", "searchBooks: ${e.localizedMessage}")}
        }
    }


}