package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow


/**
 * 與資料層的介面。
 */
interface ImagesRepository {
    fun searchImage(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData>
}