package com.manuepi.fromscratchproject.domain

import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.domain.mapper.NewsUseCaseMapper
import com.manuepi.fromscratchproject.domain.model.NewsItemUseCaseModel
import com.manuepi.fromscratchproject.entity.NewsEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class NewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsEntity: NewsEntity,
    private val newsUseCaseMapper: NewsUseCaseMapper,
) : NewsUseCase {

    override val selectedNews: Flow<NewsItemUseCaseModel?> = newsEntity.selectedItem.map {newsItem ->
        newsUseCaseMapper.mapNewsEntiyToUseCase(newsItem)
    }

    override suspend fun getNews() =
        withContext(Dispatchers.IO)
        {
            val news = newsRepository.getNews()
            newsUseCaseMapper.mapNewsRepoToUseCase(news = news)
        }

    override suspend fun updateSelectedNews(model: NewsItemUseCaseModel) {
        newsEntity.onItemSelected(newsUseCaseMapper.mapNewsItemUseCaseToEntity(model = model))
    }
}