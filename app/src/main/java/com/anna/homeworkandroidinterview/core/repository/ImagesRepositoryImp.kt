package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.core.api.ApiConfig
import com.anna.homeworkandroidinterview.core.api.NetworkService
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart


class ImagesRepositoryImp(private val networkService: NetworkService.Companion) : ImagesRepository {
    override fun searchImage(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData> = flow {
        emit(
            networkService.service.getImages(
                ApiConfig.API_KEY,
                ApiConfig.LANGUAGE_CODE,
                keywords
            )
        )
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)
}