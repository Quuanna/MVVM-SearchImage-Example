package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow


/**
 * 與資料層的介面。
 */
interface ImagesRepository {
    suspend fun searchImage(
        onStart: (() -> Unit)? = null,
        onCompletion: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null,
        keywords: String
    ): Flow<SearchImageResponseData>
}