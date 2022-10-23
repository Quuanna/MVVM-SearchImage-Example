package com.anna.homeworkandroidinterview.api

import androidx.core.os.BuildCompat
import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {

    val apiService: ImageApiService // TODO 為什麼是這樣寫


    init {
        val retrofit = with(Retrofit.Builder()) {
            baseUrl(ApiConfig.WEB_HOST)
            addConverterFactory(GsonConverterFactory.create())
            client(initOKHttpClient())
            build()
        }
        apiService = retrofit.create(ImageApiService::class.java)  // TODO apply return this 不認得
    }


    private fun initOKHttpClient(): OkHttpClient {

        val client = OkHttpClient().newBuilder().apply {
            this.connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            this.readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            this.callTimeout(ApiConfig.CALL_TIMEOUT, TimeUnit.SECONDS)
        }

        // 印Log
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            client.addInterceptor(interceptor)
        }

        return client.build()
    }

}