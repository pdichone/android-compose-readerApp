package com.bawp.areader_test.repository

import android.util.Log
import com.bawp.areader_test.model.Book
import com.bawp.areader_test.model.Item
import com.bawp.areader_test.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: BooksApi
                                        ) {
    sealed class Result {
        object LOADING : Result()
        data class Success(val itemList : List<Item>) :Result()
        data class Failure(val throwable: Throwable): Result()
    }
    suspend fun getBooks(searchQuery: String): Result {
        return try {
            val itemList = api.getAllBooks(searchQuery).items
            Log.d("ItemList", "success ${itemList.size}")
            Result.Success(itemList = itemList)
        }catch (exception:Exception){
            Log.d("MOVIELIST","failure ")

            Result.Failure(exception)
        }
    }
}