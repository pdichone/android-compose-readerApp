package com.bawp.areader_test.data

import android.util.Log
import com.bawp.areader_test.model.Item
import com.bawp.areader_test.network.GoogleBooksApiService

class BookRepository(val booksApiService: GoogleBooksApiService) {
    sealed class Result {
        object LOADING : Result()
        data class Success(val itemList : List<Item>) :Result()
        data class Failure(val throwable: Throwable): Result()
    }
    suspend fun fetchBooks(query: String):Result{
        return try {
            val itemList = booksApiService.getAllBooks(query).items
            Log.d("ItemList","success "+itemList.size)
            Result.Success(itemList = itemList)
        }catch (exception:Exception){
            Log.d("MOVIELIST","failure ")

            Result.Failure(exception)
        }
    }
}