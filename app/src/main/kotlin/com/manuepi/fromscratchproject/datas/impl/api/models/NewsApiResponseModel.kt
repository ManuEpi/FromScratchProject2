package com.manuepi.fromscratchproject.datas.impl.api.models

import com.google.gson.annotations.SerializedName

data class NewsApiResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<NewsItemApiResponseModel>
)

data class NewsItemApiResponseModel(
    @SerializedName("source") val source: NewsItemSourceApiResponseModel,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String
)

data class NewsItemSourceApiResponseModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)