package com.bawp.areader_test.model

data class Book(
    var items: List<Item>,
    val kind: String?,
    val totalItems: Int?
               )