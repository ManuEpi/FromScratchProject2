package com.manuepi.fromscratchproject.domain.mapper

import com.manuepi.fromscratchproject.domain.model.NewsItemSourceUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsItemUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel
import com.manuepi.fromscratchproject.entity.model.NewsEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsItemEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsItemSourceEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsStateEntityModel
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class NewsUseCaseMapper @Inject constructor() {
    fun mapNewsEntityToUseCase(news: NewsStateEntityModel): NewsUseCaseStateModel =
        when (news) {
            NewsStateEntityModel.Failure -> {
                NewsUseCaseStateModel.Failure
            }

            is NewsStateEntityModel.Success -> NewsUseCaseStateModel.Success(
                model = mapEntityNewsToUseCase(news.model)
            )
            NewsStateEntityModel.NotSet -> NewsUseCaseStateModel.NotSet
        }

    @VisibleForTesting
    internal fun mapEntityNewsToUseCase(model: NewsEntityModel): NewsUseCaseModel =
        NewsUseCaseModel(
            status = model.status,
            totalResults = model.totalResults,
            articles = model.articles.map { article ->
                NewsItemUseCaseModel(
                    source = article.source?.let { mapItemSource(source = it) },
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
    internal fun mapItemSource(source: NewsItemSourceEntityModel): NewsItemSourceUseCaseModel =
        NewsItemSourceUseCaseModel(
            id = source.id,
            name = source.name
        )

    fun mapNewsItemUseCaseToEntity(model: NewsItemUseCaseModel): NewsItemEntityModel =
        NewsItemEntityModel(
            source = model.source?.let { mapItemSourceToEntity(source = it) },
            author = model.author,
            title = model.title,
            description = model.description,
            url = model.url,
            urlToImage = model.urlToImage,
            publishedAt = model.publishedAt,
            content = model.content
        )

    @VisibleForTesting
    internal fun mapItemSourceToEntity(source: NewsItemSourceUseCaseModel): NewsItemSourceEntityModel =
        NewsItemSourceEntityModel(
            id = source.id,
            name = source.name
        )

    fun mapNewsEntiyToUseCase(model: NewsItemEntityModel?): NewsItemUseCaseModel? =
        model?.let {
            NewsItemUseCaseModel(
                source = it.source?.let { source -> mapItemSourceEntityToUseCase(source = source) },
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }

    @VisibleForTesting
    internal fun mapItemSourceEntityToUseCase(source: NewsItemSourceEntityModel): NewsItemSourceUseCaseModel =
        NewsItemSourceUseCaseModel(
            id = source.id,
            name = source.name
        )
}