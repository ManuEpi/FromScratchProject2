package com.manuepi.fromscratchproject.datas.impl.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsApiResponseModel(
    @Json(name = "status") val status: String,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "articles") val articles: List<NewsItemApiResponseModel>
)

@JsonClass(generateAdapter = true)
data class NewsItemApiResponseModel(
    @Json(name = "source") val source: NewsItemSourceApiResponseModel,
    @Json(name = "author") val author: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "url") val url: String,
    @Json(name = "urlToImage") val urlToImage: String,
    @Json(name = "publishedAt") val publishedAt: String,
    @Json(name = "content") val content: String
)

@JsonClass(generateAdapter = true)
data class NewsItemSourceApiResponseModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String
)