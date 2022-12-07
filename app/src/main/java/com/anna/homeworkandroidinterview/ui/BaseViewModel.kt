package com.anna.homeworkandroidinterview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel: ViewModel() {

    // Load
    val isLoadRequest: LiveData<Boolean>
        get() = mIsLoadRequest

    // error
    val responseError: LiveData<String>
        get() = mResponseError

    private val mResponseError = MutableLiveData<String>()
    private val mIsLoadRequest = MutableLiveData<Boolean>()

    val handler =  CoroutineExceptionHandler { _ , exception ->
        // error
        showLoading(false)
        mResponseError.postValue("${exception.cause}+\n+${exception.message}")

//        when(exception){
//            is SocketException -> mResponseError.postValue(exception.message)
//            is HttpException -> mResponseError.postValue(exception.message)
//            is UnknownHostException -> mResponseError.postValue(exception.message)
//        }
    }

    fun showLoading(isLoad: Boolean) {
        mIsLoadRequest.postValue(isLoad)
    }
}