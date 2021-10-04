package com.bawp.areader_test.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.Double.parseDouble

/*
 If get this error: Could not deserialize object. Class com.bawp.areader_test.model.MBook does not define a no-argument constructor. If you are using ProGuard, make sure these constructors are not stripped
  make sure to set the fields to null values or create a non-argument constructor
  check: https://stackoverflow.com/a/57278584/8442557

 */

data class MBook(
    @Exclude var id: String? = null,
    var title: String? = null,
    var authors: String? = null,
    var notes: String? = null,
    //fix: https://stackoverflow.com/questions/38681260/firebase-propertyname-doesnt-work?rq=1 (scroll down)
    @get:PropertyName("book_photo_url")
    @set:PropertyName("book_photo_url")
    var photoUrl: String? = null,
    var categories: String? = null,

    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate: String? = null,

    var rating: Double? = null,
    var description: String? = null,

    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount: String? = null,

    @get:PropertyName("started_reading_at")
    @set:PropertyName("started_reading_at")
    var startedReading: Timestamp? = null,

    @get:PropertyName("finished_reading_at")
    @set:PropertyName("finished_reading_at")
    var finishedReading: Timestamp? = null,

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String? = null,

    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId: String? = null
                ) {

//
//    fun fromDocument(data: QueryDocumentSnapshot): MBook {
//        return MBook(id = data.id,
//            title = data.get("title") as String,
//            authors = data.get("author") as String,
//            notes = data.get("notes") as String,
//            photoUrl = data.get("photo_url") as String,
//            categories = data.get("categories") as String,
//            publishedDate = data.get("published_date") as String,
//            rating = parseDouble(data.get("rating") as String),
//            description = data.get("description") as String,
//            pageCount = data.get("page_count") as Int,
//            startedReading = data.get("started_reading_at") as Timestamp,
//            finishedReading = data.get("finished_reading_at") as Timestamp,
//            userId = data.get("user_id") as String)
//    }
//
//    fun toMap(): MutableMap<String, Any> {
//        return mutableMapOf(
//            "title" to title,
//            "user_id" to userId,
//            "author" to authors,
//            "notes" to notes,
//            "photo_url" to photoUrl,
//            "published_date" to publishedDate,
//            "rating" to rating,
//            "description" to description,
//            "page_count" to pageCount,
//            "started_reading_at" to startedReading,
//            "finished_reading_at" to finishedReading,
//            "categories" to categories,)
//    }
}