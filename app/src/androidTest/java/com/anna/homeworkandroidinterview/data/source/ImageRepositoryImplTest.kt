package com.anna.homeworkandroidinterview.data.source

import com.anna.homeworkandroidinterview.core.repository.ImagesRepositoryImpl
import com.anna.homeworkandroidinterview.data.model.response.SearchImageResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageRepositoryImplTest {

    private lateinit var imagesRepositoryImpl: ImagesRepositoryImpl
    private lateinit var imageDataRemoteSource: FakeDataSource
    private lateinit var imageDataLocalDataSource: FakeDataSource
    private val remoteTasks: MutableList<SearchImageResponseData.Info?> = arrayListOf()
    private val localTasks: MutableList<SearchImageResponseData.Info?> = arrayListOf()

    @Before
    fun createRepository() {
        remoteTasks.add(
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

        localTasks.add(
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
        imageDataRemoteSource = FakeDataSource(remoteTasks)
        imageDataLocalDataSource = FakeDataSource(localTasks)
        imagesRepositoryImpl = ImagesRepositoryImpl(
            imageDataRemoteSource,
            imageDataLocalDataSource,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun remoteDataSource_requestsAllTasks() = runTest {
        imagesRepositoryImpl.searchImage(onStart = {}, onCompletion = {}, onError = {}, "貓咪")
            .collect {
                MatcherAssert.assertThat(it.dataList, IsEqual(remoteTasks))
            }
    }

    @Test
    fun localDataSource_requestsAllTasks() = runTest {
        imagesRepositoryImpl.searchImage(onStart = {}, onCompletion = {}, onError = {}, "貓咪")
            .collect {
                MatcherAssert.assertThat(it.dataList, IsEqual(localTasks))
            }

    }
}