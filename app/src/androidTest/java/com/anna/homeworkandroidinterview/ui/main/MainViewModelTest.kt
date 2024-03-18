package com.anna.homeworkandroidinterview.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anna.homeworkandroidinterview.core.api.NetworkService
import com.anna.homeworkandroidinterview.core.repository.ImagesRepository
import com.anna.homeworkandroidinterview.core.repository.ImagesRepositoryImp
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    private lateinit var imagesRepository: ImagesRepository
    private lateinit var testViewModel: MainViewModel
    private val networkService = NetworkService.Companion // TODO Add Mock NetworkService

    @Before
    fun initRepository() {
        imagesRepository = ImagesRepositoryImp(networkService)
        testViewModel = MainViewModel(imagesRepository) // Given a fresh TasksViewModel
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule() // 同步切到背景執行

    @Test
    fun callApiResponseData_notNullValue() {
        testViewModel.callApiResponseData("貓咪")  // TODO Add Mock API Response
        val value = testViewModel.getResponseImagesList.getOrAwaitValue()
        MatcherAssert.assertThat(value, not(nullValue()))
    }

    @Test
    fun searchNotFound_true() {
        testViewModel.searchNotFound(true)  // When adding a new task
        val value = testViewModel.isSearchNotFound.getOrAwaitValue()
        MatcherAssert.assertThat(value, `is`(true))
    }

    @Test
    fun searchNotFound_false() {
        testViewModel.searchNotFound(false)  // When adding a new task
        val value = testViewModel.isSearchNotFound.getOrAwaitValue()
        MatcherAssert.assertThat(value, `is`(false))
    }

    @Test
    fun showResponseError_singleEvent() {
        testViewModel.showHintMessageResponseError("SocketException", "exception.message")
        val value = testViewModel.responseError.getOrAwaitValue()
        MatcherAssert.assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun singleEventShowResponseError_notNull() {
        testViewModel.showHintMessageResponseError("SocketException", "exception.message")
        val value = testViewModel.responseError.getOrAwaitValue()
        MatcherAssert.assertThat(value, not(nullValue()))
    }

    @Test
    fun showProgress_singleEvent() {
        testViewModel.showLoading(true)
        val value = testViewModel.isShowProgress.getOrAwaitValue()
        MatcherAssert.assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun singleEventShowProgress_true() {
        testViewModel.showLoading(true)
        val event = testViewModel.isShowProgress.getOrAwaitValue()
        event.getContentIfNotHandled()?.let { value ->
            MatcherAssert.assertThat(value, `is`(true))
        }
    }


    @Test
    fun singleEventShowProgress_false() {
        testViewModel.showLoading(false)
        val event = testViewModel.isShowProgress.getOrAwaitValue()
        event.getContentIfNotHandled()?.let { value ->
            MatcherAssert.assertThat(value, `is`(false))
        }
    }
}