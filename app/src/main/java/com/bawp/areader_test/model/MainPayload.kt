package com.bawp.areader_test.model

data class MainPayload(
    var books: List<Book>,
    val kind: String?,
    val totalItems: Int?
                      )