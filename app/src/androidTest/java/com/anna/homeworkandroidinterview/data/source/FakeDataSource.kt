package com.anna.homeworkandroidinterview.data.source

import com.anna.homeworkandroidinterview.core.repository.ImageDataSource
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataSource : ImageDataSource {
    override fun searchImage(
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData> {
        return flow {}
    }
}