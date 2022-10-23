package com.anna.homeworkandroidinterview

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anna.homeworkandroidinterview.data.model.repository.ImagesRepository
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException

class MainViewModel : ViewModel() {

    // request
    val isLoadRequest: LiveData<Boolean>
        get() = mIsLoadRequest

    // response
    val getImagesList: LiveData<SearchImageResponseData>
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
    // private Repository
    private val imagesRepository = ImagesRepository()


    // 執行異步操作來獲取圖片
    fun callApiResponseData(keywords: String) {
        showLoading(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            // error
            showLoading(false)
            mResponseError.postValue("${throwable.cause}+\n+${throwable.message}")
        }) {
            imagesRepository.getApiResponse(keywords).onCompletion {
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


    /**
     * private fun
     */
    private fun showLoading(isLoad: Boolean) {
        mIsLoadRequest.postValue(isLoad)
    }
}

