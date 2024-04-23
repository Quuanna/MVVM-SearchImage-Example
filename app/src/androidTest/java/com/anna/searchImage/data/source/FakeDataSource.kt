package com.anna.searchImage.data.source

import com.anna.searchImage.core.repository.ImageDataSource
import com.anna.searchImage.data.model.response.SearchImageResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeDataSource(private var tasks: MutableList<SearchImageResponseData.Info?> = mutableListOf()) : ImageDataSource {
    override fun searchImage(
        onError: (String) -> Unit,
        keywords: String
    ): Flow<SearchImageResponseData> {
        return flow {
            emit(SearchImageResponseData(1, 1, tasks))
        }.flowOn(Dispatchers.IO)
    }
}