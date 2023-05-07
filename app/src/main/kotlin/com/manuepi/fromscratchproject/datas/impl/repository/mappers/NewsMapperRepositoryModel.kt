package com.manuepi.fromscratchproject.datas.impl.repository.mappers

import com.manuepi.fromscratchproject.NetworkResponse
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsApiResponseModel
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsItemSourceApiResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsItemRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsItemSourceRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel
import com.manuepi.fromscratchproject.entity.model.NewsEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsItemEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsItemSourceEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsStateEntityModel
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class NewsMapperRepositoryModel @Inject constructor() {
    fun mapNewsToRepository(response: NetworkResponse<NewsApiResponseModel>): NewsRepositoryStateResponseModel {
        return when (response) {
            is NetworkResponse.Error -> {
                NewsRepositoryStateResponseModel.Failure
            }

            is NetworkResponse.Success -> {
                NewsRepositoryStateResponseModel.Success(model = mapNewsItemsToRepository(data = response.data))
            }
        }
    }

    fun mapNewsToEntity(repositoryModel: NewsRepositoryStateResponseModel): NewsStateEntityModel {
        return when (repositoryModel) {
            is NewsRepositoryStateResponseModel.Failure -> {
                NewsStateEntityModel.Failure
            }

            is NewsRepositoryStateResponseModel.Success -> {
                NewsStateEntityModel.Success(model = mapNewsItemsToEntity(data = repositoryModel.model))
            }
        }
    }

    @VisibleForTesting
    internal fun mapNewsItemsToRepository(data: NewsApiResponseModel): NewsRepositoryResponseModel =
        NewsRepositoryResponseModel(
            status = data.status,
            totalResults = data.totalResults,
            articles = data.articles.map { article ->
                NewsItemRepositoryResponseModel(
                    source = article.source?.let { mapItemSourceToRepository(source = it) },
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
    internal fun mapItemSourceToRepository(source: NewsItemSourceApiResponseModel): NewsItemSourceRepositoryResponseModel =
        NewsItemSourceRepositoryResponseModel(
            id = source.id,
            name = source.name
        )

    @VisibleForTesting
    internal fun mapNewsItemsToEntity(data: NewsRepositoryResponseModel): NewsEntityModel =
        NewsEntityModel(
            status = data.status,
            totalResults = data.totalResults,
            articles = data.articles.map { article ->
                NewsItemEntityModel(
                    source = article.source?.let { mapItemSourceToEntity(source = it) },
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
    internal fun mapItemSourceToEntity(source: NewsItemSourceRepositoryResponseModel): NewsItemSourceEntityModel =
        NewsItemSourceEntityModel(
            id = source.id,
            name = source.name
        )
}