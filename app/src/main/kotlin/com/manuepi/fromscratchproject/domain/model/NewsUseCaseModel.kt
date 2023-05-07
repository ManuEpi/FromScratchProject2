package com.manuepi.fromscratchproject.domain.model

data class NewsUseCaseModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<NewsItemUseCaseModel>
)

data class NewsItemUseCaseModel(
    val source: NewsItemSourceUseCaseModel?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class NewsItemSourceUseCaseModel(
    val id: String?,
    val name: String?
)