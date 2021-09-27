package com.bawp.areader_test

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReaderApplication: Application() {
    override fun onCreate(){
        super.onCreate()
    }

}