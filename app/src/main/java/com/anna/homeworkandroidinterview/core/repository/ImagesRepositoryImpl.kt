package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart


class ImagesRepositoryImpl(
    private val imageDataRemoteSource: ImageDataSource,
    private val imageDataLocalSource: ImageDataSource
    // Local Data
) : ImagesRepository {
    override fun searchImage(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData> = flow {
        imageDataRemoteSource.searchImage(
            onError = { searchImageLocal(keywords) { onError(it) } }, keywords
        ).collect {
            emit(it)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    private fun searchImageLocal(keywords: String, onError: (String) -> Unit) {
        imageDataLocalSource.searchImage(onError = { onError(it) }, keywords = keywords)
    }
}