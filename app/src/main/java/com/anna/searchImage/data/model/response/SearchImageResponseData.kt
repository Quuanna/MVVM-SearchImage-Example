package com.anna.searchImage.data.model.response

import com.google.gson.annotations.SerializedName

data class SearchImageResponseData(
    // 影象點選總數
    @SerializedName("total")
    val total: Int?,
    // API僅限於每個查詢最多返回500張影象
    @SerializedName("totalHits")
    val totalHits: Int?,
    // 查詢資料結果
    @SerializedName("hits")
    val dataList: List<Info?>
) {
    data class Info(
        @SerializedName("id")
        val imageSearchId: Int?,
        @SerializedName("pageURL")
        val webPageURL: String?,
        @SerializedName("type")
        val imageType: String?,
        @SerializedName("tags")
        val imageTags: String?,
        @SerializedName("previewURL")
        val previewURL: String?,
        @SerializedName("previewWidth")
        val previewWidth: Int?,
        @SerializedName("previewHeight")
        val previewHeight: Int?,
        @SerializedName("webformatURL")
        val webFormatURL: String?,
        @SerializedName("webformatWidth")
        val webFormatWidth: Int?,
        @SerializedName("webformatHeight")
        val webFormatHeight: Int?,
        @SerializedName("largeImageURL")
        val largeImageURL: String?,
        @SerializedName("imageWidth")
        val imageWidth: Int?,
        @SerializedName("imageHeight")
        val imageHeight: Int?,
        @SerializedName("imageSize")
        val imageSize: Int?,
        @SerializedName("views")
        val lookThroughTotal: Int?,
        @SerializedName("downloads")
        val downloadsTotal: Int?,
        @SerializedName("collections")
        val collectionsTotal: Int?,
        @SerializedName("likes")
        val likedTotal: Int?,
        @SerializedName("comments")
        val commentsTotal: Int?,
        @SerializedName("user_id")
        val userId: Int?,
        @SerializedName("user")
        val userName: String?,
        @SerializedName("userImageURL")
        val userMugShotImageURL: String?
    )
}

