package com.bawp.areader_test.di

import com.bawp.areader_test.network.BooksApi
import com.bawp.areader_test.repository.BookRepository
import com.bawp.areader_test.repository.FireRepository
import com.bawp.areader_test.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideBookRepository(
        api: BooksApi
                             ) = BookRepository(api)

    @Singleton
    @Provides
    fun provideFireBookRepository(
                             ) = FireRepository(queryBooks = FirebaseFirestore.getInstance()
        .collection("books")
        //TODO: show recently added first
       /* .orderBy("title", Query.Direction.DESCENDING)*/
                                               )

    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

    //for Firestore stuff: https://medium.com/firebase-developers/how-to-display-data-from-firestore-using-jetpack-compose-49ee736dc07d

//    @Provides
//    @Singleton
//    fun provideQueryBooks() = FirebaseFirestore.getInstance()
//        .collection("books")
//       // .orderBy(NAME_PROPERTY, ASCENDING)
//

}