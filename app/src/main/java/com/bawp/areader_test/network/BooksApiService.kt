package com.bawp.areader_test.network

import com.bawp.areader_test.model.Book
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Class that defines all the interfaces for
 * the api calls we need. (Used for retrofit calls).
 */
private const val BASE_URL = "https://www.googleapis.com/books/v1/"

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()



interface GoogleBooksApiService {
    //@GET("volumes?q=query")
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book
//
//    @GET("volumes?q=android")
//    suspend fun getAll(): Book
}

object BooksApi {
    val retrofitService: GoogleBooksApiService by lazy {
        retrofit.create(GoogleBooksApiService::class.java)

    }
}