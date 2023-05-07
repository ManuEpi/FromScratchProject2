package com.manuepi.fromscratchproject.entity.model

data class NewsEntityModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<NewsItemEntityModel>
)
data class NewsItemEntityModel(
    val source: NewsItemSourceEntityModel?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class NewsItemSourceEntityModel(
    val id: String?,
    val name: String?
)