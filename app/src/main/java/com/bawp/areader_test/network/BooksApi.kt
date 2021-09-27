package com.bawp.areader_test.network

import com.bawp.areader_test.model.Book
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    //@GET("volumes?q=query")
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

}