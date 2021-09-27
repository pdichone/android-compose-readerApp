package com.bawp.areader_test.network

import com.bawp.areader_test.model.Book
import com.bawp.areader_test.model.MainPayload
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    //@GET("volumes?q=query")
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book
    //Book represents the entire json payload
    //Item is the

    //Get a book detail with a book id
    //https://www.googleapis.com/books/v1/volumes/buc0AAAAMAAJ

    @GET("volumes/")
    suspend fun getBookInfo(@Query("bookId") query: String): Book
}