package com.anna.searchImage.data.source

import com.anna.searchImage.core.repository.ImagesRepository
import com.anna.searchImage.data.model.response.SearchImageResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class FakeImagesRepositoryImpl(private var tasks: MutableList<SearchImageResponseData.Info?> = mutableListOf()) : ImagesRepository {

    override suspend fun searchImage(
        onStart: (() -> Unit)?,
        onCompletion: (() -> Unit)?,
        onError: ((String) -> Unit)?,
        keywords: String
    ): Flow<SearchImageResponseData> {
        return flow {

            if (tasks.isEmpty()) {
                emit(SearchImageResponseData(1, 1, arrayListOf()))
            } else {
                emit(SearchImageResponseData(1, 1, tasks))
            }


        }.onStart { onStart?.invoke() }.onCompletion { onCompletion?.invoke() }.flowOn(Dispatchers.IO)
    }
}