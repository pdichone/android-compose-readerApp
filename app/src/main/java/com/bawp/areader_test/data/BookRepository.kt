package com.bawp.areader_test.data

import android.util.Log
<<<<<<< HEAD:app/src/main/java/com/bawp/areader_test/repository/BookRepository.kt
import com.bawp.areader_test.model.Book
import com.bawp.areader_test.model.MainPayload
import com.bawp.areader_test.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: BooksApi
                                        ) {
=======
import com.bawp.areader_test.model.Item
import com.bawp.areader_test.network.GoogleBooksApiService
>>>>>>> parent of 983f2eb (Hilt, repository and Viewmodel setup):app/src/main/java/com/bawp/areader_test/data/BookRepository.kt

class BookRepository(val booksApiService: GoogleBooksApiService) {
    sealed class Result {
        object LOADING : Result()
        data class Success(val bookList : List<Book>) :Result()
        data class Successs(val book : Book) :Result()
        data class Failure(val throwable: Throwable): Result()
    }
<<<<<<< HEAD:app/src/main/java/com/bawp/areader_test/repository/BookRepository.kt

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
=======
    suspend fun fetchBooks(query: String):Result{
        return try {
            val itemList = booksApiService.getAllBooks(query).items
            Log.d("ItemList","success "+itemList.size)
            Result.Success(itemList = itemList)
>>>>>>> parent of 983f2eb (Hilt, repository and Viewmodel setup):app/src/main/java/com/bawp/areader_test/data/BookRepository.kt
        }catch (exception:Exception){
            Log.d("MOVIELIST","failure ${exception.localizedMessage}")

            Result.Failure(exception)
        }
    }
}