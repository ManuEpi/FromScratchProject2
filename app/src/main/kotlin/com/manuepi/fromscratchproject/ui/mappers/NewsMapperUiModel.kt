package com.manuepi.fromscratchproject.ui.mappers

import com.manuepi.fromscratchproject.domain.model.NewsItemSourceUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseModel
import com.manuepi.fromscratchproject.ui.models.NewsItemSourceUiModel
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.models.NewsUiModel
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class NewsMapperUiModel @Inject constructor() {
    fun mapUseCaseResponseToUi(response: NewsUseCaseModel): NewsUiModel =
        NewsUiModel(
            totalResults = response.totalResults,
            articles = response.articles.map { article ->
                NewsItemUiModel(
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
    internal fun mapItemSource(source: NewsItemSourceUseCaseModel): NewsItemSourceUiModel =
        NewsItemSourceUiModel(
            id = source.id,
            name = source.name
        )
}