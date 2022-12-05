package com.anna.homeworkandroidinterview.ui.main

import androidx.lifecycle.*
import com.anna.homeworkandroidinterview.core.repository.ImagesRepository
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class MainViewModel(private val imagesRepository: ImagesRepository) : ViewModel() {

    // request
    val isLoadRequest: LiveData<Boolean>
        get() = mIsLoadRequest

    // response
    val getResponseImagesList: LiveData<SearchImageResponseData>
        get() = mImagesList

    // 搜不到資料
    val isSearchNotFound: LiveData<Boolean>
        get() = mIsSearchNotFound

    // error
    val responseError: LiveData<String>
        get() = mResponseError

    // private LiveData
    private val mIsLoadRequest = MutableLiveData<Boolean>()
    private val mImagesList = MutableLiveData<SearchImageResponseData>()
    private val mIsSearchNotFound = MutableLiveData<Boolean>()
    private val mResponseError = MutableLiveData<String>()


    // 執行異步操作來獲取圖片
    fun callApiResponseData(keyword: String) {
        showLoading(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            // error
            showLoading(false)
            mResponseError.postValue("${throwable.cause}+\n+${throwable.message}")
        }) {
            imagesRepository.getApiResponse(keyword).onCompletion {
                // 完成
                showLoading(false)
            }.collect {
                showLoading(false)
                if (it.dataList.isEmpty()) {
                    mIsSearchNotFound.postValue(true)
                } else {
                    mImagesList.postValue(it)
                }
            }
        }
    }

    private fun showLoading(isLoad: Boolean) {
        mIsLoadRequest.postValue(isLoad)
    }
}

