package com.manuepi.fromscratchproject.ui.mappers

import com.manuepi.fromscratchproject.domain.model.NewsItemSourceUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsItemUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseModel
import com.manuepi.fromscratchproject.ui.models.NewsItemSourceUiModel
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.models.NewsUiModel
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class NewsMapperUiModel @Inject constructor() {
    fun mapUseCaseResponseToUi(response: NewsUseCaseModel): NewsUiModel =
        NewsUiModel(totalResults = response.totalResults,
            articles = response.articles.map { article ->
                NewsItemUiModel(
                    source = article.source?.let { mapItemSource(source = it) },
                    author = article.author,
                    title = article.title,
                    description = article.description,
                    url = article.url,
                    urlToImage = article.urlToImage,
                    publishedAt = article.publishedAt,
                    content = article.content

                )
            })

    fun mapUseCaseItemResponseToUi(item: NewsItemUseCaseModel?): NewsItemUiModel? =
        item?.let {
            NewsItemUiModel(
                source = it.source?.let { source -> mapItemSource(source = source) },
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
    internal fun mapItemSource(source: NewsItemSourceUseCaseModel): NewsItemSourceUiModel =
        NewsItemSourceUiModel(
            id = source.id, name = source.name
        )

    fun mapUiToUseCase(uiModel: NewsItemUiModel): NewsItemUseCaseModel = NewsItemUseCaseModel(
        source = uiModel.source?.let { mapItemSourceToUseCase(source = it) },
        author = uiModel.author,
        title = uiModel.title,
        description = uiModel.description,
        url = uiModel.url,
        urlToImage = uiModel.urlToImage,
        publishedAt = uiModel.publishedAt,
        content = uiModel.content
    )

    @VisibleForTesting
    internal fun mapItemSourceToUseCase(source: NewsItemSourceUiModel): NewsItemSourceUseCaseModel =
        NewsItemSourceUseCaseModel(
            id = source.id, name = source.name
        )
}