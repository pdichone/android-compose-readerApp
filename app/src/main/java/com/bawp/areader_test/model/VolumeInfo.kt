package com.bawp.areader_test.model

data class VolumeInfo(

    val authors: List<String>?,
    val categories: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val pageCount: Int?,
    val publishedDate: String?,
    val publisher: String?,
    //val subtitle: String,
    val title: String?
                     )