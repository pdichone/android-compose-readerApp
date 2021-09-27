package com.bawp.areader_test.screens.details

import androidx.lifecycle.ViewModel
import com.bawp.areader_test.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor( private val repository: BookRepository)
    : ViewModel(){

        suspend fun getBookInfo(bookId: String): BookRepository.Result{
            return repository.getBookInfo(bookId)

        }

}