package com.anna.homeworkandroidinterview.data.source

import com.anna.homeworkandroidinterview.core.repository.ImageDataSource
import com.anna.homeworkandroidinterview.core.repository.ImagesRepository
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow

class ImageRepositoryImplTest : ImagesRepository {

    private lateinit var imageDataRemoteSource: ImageDataSource
    private lateinit var imageDataLocalDataSource: ImageDataSource
    override fun searchImage(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData> {
        TODO("Not yet implemented")
    }
}