package com.manuepi.fromscratchproject.ui.models

data class NewsUiModel(
    val totalResults: Int,
    val articles: List<NewsItemUiModel>
)

data class NewsItemUiModel(
    val source: NewsItemSourceUiModel,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

data class NewsItemSourceUiModel(
    val id: String,
    val name: String
)