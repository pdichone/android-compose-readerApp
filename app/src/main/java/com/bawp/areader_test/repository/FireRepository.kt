package com.bawp.areader_test.repository

import android.util.Log
import com.bawp.areader_test.data.DataOrException
import com.bawp.areader_test.model.MBook
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query //must change from .core.Query to .Query !!!
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireRepository @Inject constructor(
    private val queryBooks: Query
                                        ) {

    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {

        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {

            dataOrException.loading = true
            //In order to get the await, we need to add the: implementation "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.x" to gradle
         dataOrException.data = queryBooks.get().await().documents.map { documentSnapshot ->
             documentSnapshot.toObject(MBook::class.java)!!
         }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
           // Log.d("Inside", "getAllBooksFromDatabase: ${dataOrException.data?.toList()}")
        }catch (e: FirebaseFirestoreException){
            dataOrException.e = e
        }

        return dataOrException


    }

//    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Exception> {
//
//        val collection = FirebaseFirestore.getInstance().collection("books")
//        val dataOrException = DataOrException<List<MBook>, Exception>()
//        try {
//
//            collection.addSnapshotListener {response, error ->
//                //Log.d("Snap", "getAllBooksFromDatabase: ${response?.documents.toString()}")
//                if (error == null){
//                    dataOrException.data =
//                        response?.documents?.map { documentSnapshot ->
//                        documentSnapshot.toObject(MBook::class.java)!!
//
//
//                    }
//                    Log.d("Snap", "getAllBooksFromDatabase: ${dataOrException.data.toString()}")
//
//                }
//
//            }
//
//        }catch (e: FirebaseFirestoreException){
//            dataOrException.e = e
//        }
//        //Log.d("Inside", "getAllBooksFromDatabase: ${dataOrException.data?.toList()}")
//        return dataOrException
//
//
//    }
}