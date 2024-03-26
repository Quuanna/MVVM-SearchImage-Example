package com.anna.homeworkandroidinterview.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import com.anna.homeworkandroidinterview.data.source.FakeImagesRepositoryImpl
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
    private lateinit var fakeImageRepository: FakeImagesRepositoryImpl
    private lateinit var testViewModel: MainViewModel

    @Before
    fun initRepository() {
        val list: ArrayList<SearchImageResponseData.Info?> = arrayListOf()
        list.add(
            SearchImageResponseData.Info(
                imageSearchId = 1264258,
                webPageURL = "https://pixabay.com/zh/photos/pet-cuteness-cat-gaze-animal-1264258/",
                imageType = "photo",
                imageTags = "寵物, 動物, 可愛",
                previewURL = "https://cdn.pixabay.com/photo/2016/03/18/01/47/pet-1264258_150.jpg",
                previewWidth = 150,
                previewHeight = 99,
                webFormatURL = "https://pixabay.com/get/g8383d1556a2ee4d012d372dd425f192aae5509f5cf3eb6fd1cf506082833af83706191d09ccc360fb82c88a1200013e286f0c8b4a725c033c7914e1882a4a01d_640.jpg",
                webFormatWidth = 640,
                webFormatHeight = 426,
                largeImageURL = "https://pixabay.com/get/g6c3ef9fe30319491b6c82b1820628c4b8e505efa449331a7d63283df5c5c8651cb87ce1627599ace4603702ccda283028a265d19fc74b8460df9288fb10b3485_1280.jpg",
                imageWidth = 3600,
                imageHeight = 2403,
                imageSize = 2264002,
                lookThroughTotal = 7092,
                downloadsTotal = 3704,
                collectionsTotal = 78,
                likedTotal = 28,
                commentsTotal = 1,
                userId = 2106891,
                userName = "sam651030",
                userMugShotImageURL = "https://cdn.pixabay.com/user/2016/03/18/01-46-46-990_250x250.jpg"
            )
        )
        fakeImageRepository = FakeImagesRepositoryImpl(list) // 假資料
        testViewModel = MainViewModel(fakeImageRepository) // Given a fresh TasksViewModel
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule() // 同步切到背景執行

    @Test
    fun 打API後確認有資料() {
        testViewModel.callApiResponseData("貓咪")
        val value = testViewModel.getResponseImagesList.getOrAwaitValue()
        MatcherAssert.assertThat(value, not(nullValue()))
    }

    @Test
    fun 打API後沒有資料() {
        fakeImageRepository = FakeImagesRepositoryImpl(arrayListOf())
        testViewModel = MainViewModel(fakeImageRepository) // Given a fresh TasksViewModel

        testViewModel.callApiResponseData("貓咪")
        val value = testViewModel.isSearchNotFound.getOrAwaitValue()
        MatcherAssert.assertThat(value, `is`(true))
    }

    @Test
    fun 搜尋未找到_true() {
        testViewModel.searchNotFound(true)  // When adding a new task
        val value = testViewModel.isSearchNotFound.getOrAwaitValue()
        MatcherAssert.assertThat(value, `is`(true))
    }

    @Test
    fun 搜尋未找到_false() {
        testViewModel.searchNotFound(false)  // When adding a new task
        val value = testViewModel.isSearchNotFound.getOrAwaitValue()
        MatcherAssert.assertThat(value, `is`(false))
    }

    @Test
    fun API回應錯誤_顯示單一事件視窗邏輯是否正確() {
        testViewModel.showExceptionMessageError("SocketException", "exception.message")
        val value = testViewModel.responseError.getOrAwaitValue()
        MatcherAssert.assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun 顯示API回應錯誤不為空() {
        testViewModel.showExceptionMessageError("SocketException", "exception.message")
        val value = testViewModel.responseError.getOrAwaitValue()
        MatcherAssert.assertThat(value, not(nullValue()))
    }

    @Test
    fun 畫面進度_顯示單一事件的邏輯是否正確() {
        testViewModel.showLoading(true)
        val value = testViewModel.isShowProgress.getOrAwaitValue()
        MatcherAssert.assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun 單一事件顯示進度_true() {
        testViewModel.showLoading(true)
        val event = testViewModel.isShowProgress.getOrAwaitValue()
        event.getContentIfNotHandled()?.let { value ->
            MatcherAssert.assertThat(value, `is`(true))
        }
    }


    @Test
    fun 單一事件顯示進度_false() {
        testViewModel.showLoading(false)
        val event = testViewModel.isShowProgress.getOrAwaitValue()
        event.getContentIfNotHandled()?.let { value ->
            MatcherAssert.assertThat(value, `is`(false))
        }
    }
}