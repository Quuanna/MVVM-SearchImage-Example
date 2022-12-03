package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.core.api.ApiConfig
import com.anna.homeworkandroidinterview.core.api.NetworkService
import com.anna.homeworkandroidinterview.core.api.NetworkService.Companion.mNetworkService
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ImagesRepository {

    suspend fun getApiResponse(keywords: String): Flow<SearchImageResponseData> {
        return flow {
            val response =
                mNetworkService.getImages(
                    ApiConfig.API_KEY,
                    ApiConfig.LANGUAGE_CODE,
                    keywords
                )
            // 此方法回傳
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}