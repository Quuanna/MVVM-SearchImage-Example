package com.anna.homeworkandroidinterview

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.anna.homeworkandroidinterview.core.api.NetworkService
import com.anna.homeworkandroidinterview.core.repository.ImagesRepository
import com.anna.homeworkandroidinterview.core.repository.ImagesRepositoryImpl
import com.anna.homeworkandroidinterview.core.repository.source.local.ImagesLocalDataSource
import com.anna.homeworkandroidinterview.core.repository.source.remote.ImagesRemoteDataSource

/**
 * Service Locator Pattern
 * Repository:  init、rest
 * DataSource: 處理 Remote、local
 *
 */
object ServiceLocator {

    private val lock = Any()
    // TODO　add DataBase

    @Volatile
    var imagesRepository: ImagesRepository? = null
        @VisibleForTesting set


    fun provideRepository(context: Context): ImagesRepository {
        synchronized(this) {
            return imagesRepository ?: createRepository(context)
        }
    }


    private fun createRepository(context: Context): ImagesRepository {
        return ImagesRepositoryImpl(
            ImagesRemoteDataSource(NetworkService),
            ImagesLocalDataSource(context) // TODO 建立 Room 之後要換成 createLocalRepository(context)
        )
    }

    private fun createLocalRepository(context: Context): ImagesLocalDataSource {
        // TODO　實作 Room
        createDataBase(context)
        return ImagesLocalDataSource(context)
    }

    private fun createDataBase(context: Context) {
        // Room.databaseBuilder 需要 context
    }


    @VisibleForTesting
    fun restRepository() {
        synchronized(lock) {
            // TODO detail images Data，such as remote、local
        }
    }

}