package com.manuepi.fromscratchproject.datas.models

data class NewsRepositoryResponseModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<NewsItemRepositoryResponseModel>
)

data class NewsItemRepositoryResponseModel(
    val source: NewsItemSourceRepositoryResponseModel?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class NewsItemSourceRepositoryResponseModel(
    val id: String?,
    val name: String?
)