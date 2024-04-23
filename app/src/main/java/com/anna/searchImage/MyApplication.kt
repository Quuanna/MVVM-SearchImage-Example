package com.anna.searchImage

import android.app.Application
import com.anna.searchImage.core.repository.ImagesRepository

class MyApplication : Application() {

    val imagesRepository: ImagesRepository get() = ServiceLocator.provideRepository(this)
    override fun onCreate() {
        super.onCreate()
    }
}