package com.anna.homeworkandroidinterview.data.model.repository

import com.anna.homeworkandroidinterview.api.ApiConfig
import com.anna.homeworkandroidinterview.api.NetworkService
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ImagesRepository {

    suspend fun getApiResponse(keywords: String): Flow<SearchImageResponseData> {
        return flow {
            val response =
                NetworkService().apiService.getImages(
                    ApiConfig.API_KEY,
                    ApiConfig.LANGUAGE_CODE,
                    keywords
                )
            // 此方法回傳
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}