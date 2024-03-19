package com.anna.homeworkandroidinterview.core.repository.source.remote

import com.anna.homeworkandroidinterview.core.api.ApiConfig
import com.anna.homeworkandroidinterview.core.api.NetworkService
import com.anna.homeworkandroidinterview.core.repository.ImageDataSource
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImagesRemoteDataSource(
    private val networkService: NetworkService.Companion
) : ImageDataSource {
    override fun searchImage(
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData> {
        return flow {
            val response = networkService.service.getImages(
                ApiConfig.API_KEY,
                ApiConfig.LANGUAGE_CODE,
                keywords
            )

            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                onError(response.errorBody().toString())
            }
        }
    }
}