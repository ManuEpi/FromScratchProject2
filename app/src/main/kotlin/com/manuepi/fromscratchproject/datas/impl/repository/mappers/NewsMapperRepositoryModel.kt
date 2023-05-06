package com.manuepi.fromscratchproject.datas.impl.repository.mappers

import com.manuepi.fromscratchproject.NetworkResponse
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsApiResponseModel
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsItemSourceApiResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsItemRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsItemSourceRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class NewsMapperRepositoryModel @Inject constructor() {
    fun mapNews(response: NetworkResponse<NewsApiResponseModel>): NewsRepositoryStateResponseModel {
        return when (response) {
            is NetworkResponse.Error -> {
                NewsRepositoryStateResponseModel.Failure
            }

            is NetworkResponse.Success -> {
                NewsRepositoryStateResponseModel.Success(model = mapNewsItems(data = response.data))
            }
        }
    }

    @VisibleForTesting
    internal fun mapNewsItems(data: NewsApiResponseModel): NewsRepositoryResponseModel =
        NewsRepositoryResponseModel(
            status = data.status,
            totalResults = data.totalResults,
            articles = data.articles.map { article ->
                NewsItemRepositoryResponseModel(
                    source = mapItemSource(source = article.source),
                    author = article.author,
                    title = article.title,
                    description = article.description,
                    url = article.url,
                    urlToImage = article.urlToImage,
                    publishedAt = article.publishedAt,
                    content = article.content

                )
            }
        )

    @VisibleForTesting
    internal fun mapItemSource(source: NewsItemSourceApiResponseModel): NewsItemSourceRepositoryResponseModel =
        NewsItemSourceRepositoryResponseModel(
            id = source.id,
            name = source.name
        )
}