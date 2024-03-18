package com.anna.homeworkandroidinterview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anna.homeworkandroidinterview.util.SingleEvent
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel: ViewModel() {

    // Load
    val isShowProgress: LiveData<SingleEvent<Boolean>>
        get() = setIsShowProgress

    // error
    val responseError: LiveData<SingleEvent<String>>
        get() = setResponseError

    private val setResponseError = MutableLiveData<SingleEvent<String>>()
    private val setIsShowProgress = MutableLiveData<SingleEvent<Boolean>>()

    val handler =  CoroutineExceptionHandler { _ , exception ->
        // error
        showHintMessageResponseError(exception.cause.toString(), exception.message)

//        when(exception){
//            is SocketException -> mResponseError.postValue(exception.message)
//            is HttpException -> mResponseError.postValue(exception.message)
//            is UnknownHostException -> mResponseError.postValue(exception.message)
//        }
    }

    fun showHintMessageResponseError(cause: String?, message: String?) {
        if (!cause.isNullOrEmpty() && !message.isNullOrEmpty()) {
            setResponseError.postValue(SingleEvent("${cause}+\n+${message}"))
        }

    }

    fun showLoading(isLoad: Boolean) {
        setIsShowProgress.postValue(SingleEvent(isLoad))
    }
}