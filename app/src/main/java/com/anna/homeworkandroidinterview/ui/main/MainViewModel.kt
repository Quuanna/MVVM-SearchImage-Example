package com.anna.homeworkandroidinterview.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anna.homeworkandroidinterview.core.repository.ImagesRepository
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import com.anna.homeworkandroidinterview.ui.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val imagesRepository: ImagesRepository) : BaseViewModel() {

    // response
    val getResponseImagesList: LiveData<SearchImageResponseData>
        get() = setImagesList

    // 搜不到資料
    val isSearchNotFound: LiveData<Boolean>
        get() = setIsSearchNotFound


    // private LiveData
    private val setImagesList = MutableLiveData<SearchImageResponseData>()
    private val setIsSearchNotFound = MutableLiveData<Boolean>()


    fun searchNotFound(isNotFoundData: Boolean) {
        setIsSearchNotFound.postValue(isNotFoundData)
    }

    // 執行異步操作來獲取圖片
    fun callApiResponseData(keyword: String) {
        viewModelScope.launch(handlerException) {
            imagesRepository.searchImage(
                onStart = { showLoading(true) },
                onCompletion = { showLoading(false) },
                onError = { showServiceMessageError(it) },
                keyword
            ).collect {
                if (it.dataList.isEmpty()) {
                    searchNotFound(true)
                } else {
                    setImagesList.postValue(it)
                }
            }
        }
    }
}

