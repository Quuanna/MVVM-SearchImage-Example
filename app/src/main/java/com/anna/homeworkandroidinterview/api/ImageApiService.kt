package com.anna.homeworkandroidinterview.api

import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageApiService {

    @GET(ApiConfig.WEB_HOST+"/api/")
    suspend fun getImages(
        @Query("key") apiKey: String,
        @Query("lang") languageCode: String,
        @Query("q") searchContent: String?
    ): SearchImageResponseData
}
