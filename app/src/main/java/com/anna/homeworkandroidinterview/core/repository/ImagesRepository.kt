package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.core.api.ApiConfig
import com.anna.homeworkandroidinterview.core.api.NetworkService
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData


class ImagesRepository(private val mNetworkService: NetworkService.Companion) {

//    suspend fun getApiResponse(keywords: String): Flow<SearchImageResponseData> {
//        return flow {
//            val response =
//                mNetworkService.getImages(
//                    ApiConfig.API_KEY,
//                    ApiConfig.LANGUAGE_CODE,
//                    keywords
//                )
//            // 此方法回傳
//            emit(response)
//        }.flowOn(Dispatchers.IO)
//    }

    suspend fun getSearchImage(keywords: String, emit: (SearchImageResponseData) -> Unit){
        val response = mNetworkService.mNetworkService.getImages(
            ApiConfig.API_KEY,
            ApiConfig.LANGUAGE_CODE,
            keywords
        )
        emit(response)
    }


}