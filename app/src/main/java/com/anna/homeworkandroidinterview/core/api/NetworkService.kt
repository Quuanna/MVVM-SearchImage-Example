package com.anna.homeworkandroidinterview.core.api

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {

    val apiService: ImageApiService

    init {
        val retrofit = with(Retrofit.Builder()) {
            baseUrl(ApiConfig.WEB_HOST)
            addConverterFactory(GsonConverterFactory.create())
            client(initOKHttpClient())
            build()
        }
        apiService = retrofit.create(ImageApiService::class.java)
    }


    private fun initOKHttpClient(): OkHttpClient {

        val client = with(OkHttpClient().newBuilder()){
            connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            callTimeout(ApiConfig.CALL_TIMEOUT, TimeUnit.SECONDS)
        }

        // 印Log
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            client.addInterceptor(interceptor)
        }

        return client.build()
    }

}