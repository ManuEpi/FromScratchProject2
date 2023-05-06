package com.manuepi.fromscratchproject.domain.mapper

import com.manuepi.fromscratchproject.datas.models.NewsItemSourceRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel
import com.manuepi.fromscratchproject.domain.model.NewsItemSourceUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsItemUseCaseeModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class NewsUseCaseMapper @Inject constructor() {
    fun mapNewsRepoToUseCase(news: NewsRepositoryStateResponseModel): NewsUseCaseStateModel =
        when (news) {
            NewsRepositoryStateResponseModel.Failure -> {
                NewsUseCaseStateModel.Failure
            }

            is NewsRepositoryStateResponseModel.Success -> NewsUseCaseStateModel.Success(
                model = mapRepositoryResponseToUseCase(news.model)
            )
        }

    @VisibleForTesting
    internal fun mapRepositoryResponseToUseCase(response: NewsRepositoryResponseModel): NewsUseCaseModel =
        NewsUseCaseModel(
            status = response.status,
            totalResults = response.totalResults,
            articles = response.articles.map { article ->
                NewsItemUseCaseeModel(
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
    internal fun mapItemSource(source: NewsItemSourceRepositoryResponseModel): NewsItemSourceUseCaseModel =
        NewsItemSourceUseCaseModel(
            id = source.id,
            name = source.name
        )
}