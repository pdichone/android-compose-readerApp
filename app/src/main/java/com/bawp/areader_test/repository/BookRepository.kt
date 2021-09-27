package com.bawp.areader_test.repository

import android.util.Log
import com.bawp.areader_test.model.Book
import com.bawp.areader_test.model.MainPayload
import com.bawp.areader_test.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: BooksApi
                                        ) {

    sealed class Result {
        object LOADING : Result()
        data class Success(val bookList : List<Book>) :Result()
        data class Successs(val book : Book) :Result()
        data class Failure(val throwable: Throwable): Result()
    }

    suspend fun getBookInfo(bookId: String): Result {
        return try {
            val bookInfo = api.getBookInfo(bookId)
            Log.d("BookInfo", "success $bookInfo")
            Result.Successs(book = bookInfo)


        }catch (exception: Exception) {
            Result.Failure(exception)

        }
    }
    suspend fun getBooks(searchQuery: String): Result {
        return try {
            val itemList = api.getAllBooks(searchQuery)
            Log.d("ItemList", "success ${itemList}")
            Result.Success(bookList = itemList)
        }catch (exception:Exception){
            Log.d("MOVIELIST","failure ${exception.localizedMessage}")

            Result.Failure(exception)
        }
    }
}