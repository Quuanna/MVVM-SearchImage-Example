package com.anna.homeworkandroidinterview.ui.main

import androidx.lifecycle.*
import com.anna.homeworkandroidinterview.core.repository.ImagesRepository
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import com.anna.homeworkandroidinterview.ui.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val imagesRepository: ImagesRepository) : BaseViewModel() {


    // response
    val getResponseImagesList: LiveData<SearchImageResponseData>
        get() = mImagesList

    // 搜不到資料
    val isSearchNotFound: LiveData<Boolean>
        get() = mIsSearchNotFound


    // private LiveData
    private val mImagesList = MutableLiveData<SearchImageResponseData>()
    private val mIsSearchNotFound = MutableLiveData<Boolean>()



    // 執行異步操作來獲取圖片
    fun callApiResponseData(keyword: String) {
        showLoading(true)
        viewModelScope.launch(handler) {
            // Flow
//            imagesRepository.getApiResponse(keyword).onCompletion {
//                // 完成
//                showLoading(false)
//            }.collect {
//                showLoading(false)
//                if (it.dataList.isEmpty()) {
//                    mIsSearchNotFound.postValue(true)
//                } else {
//                    mImagesList.postValue(it)
//                }
//            }

            imagesRepository.getSearchImage(keyword){
                if (it.dataList.isEmpty()) {
                    mIsSearchNotFound.postValue(true)
                } else {
                    mImagesList.postValue(it)
                }
            }
        }
    }

}

