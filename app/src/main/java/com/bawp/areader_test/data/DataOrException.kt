package com.bawp.areader_test.data

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null)