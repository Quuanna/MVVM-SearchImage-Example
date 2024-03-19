package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow

/**
 * 存取任務資料的主要入口點。
 */
interface ImageDataSource {
    fun searchImage(
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData>
}