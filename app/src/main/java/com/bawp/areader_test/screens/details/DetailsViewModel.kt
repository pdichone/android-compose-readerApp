package com.bawp.areader_test.screens.details

import androidx.lifecycle.ViewModel
import com.bawp.areader_test.model.Item
import com.bawp.areader_test.repository.BookRepository
import com.bawp.areader_test.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository)
    :ViewModel() {
        suspend fun getBookInfo(bookId: String): Resource<Item> {
            return repository.getBookInfo(bookId)
        }



    }