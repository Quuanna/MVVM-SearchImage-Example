package com.anna.homeworkandroidinterview

import android.app.Application
import com.anna.homeworkandroidinterview.core.repository.ImagesRepository

class MyApplication : Application() {

    val imagesRepository: ImagesRepository get() = ServiceLocator.provideRepository(this)
    override fun onCreate() {
        super.onCreate()
    }
}