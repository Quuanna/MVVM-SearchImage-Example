package com.anna.homeworkandroidinterview.core.repository

import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun searchImage(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData>
}