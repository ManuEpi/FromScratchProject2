package com.manuepi.fromscratchproject.domain

import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.datas.impl.repository.mappers.NewsMapperRepositoryModel
import com.manuepi.fromscratchproject.domain.mapper.NewsUseCaseMapper
import com.manuepi.fromscratchproject.domain.model.NewsItemUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel
import com.manuepi.fromscratchproject.entity.NewsEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class NewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsEntity: NewsEntity,
    private val newsUseCaseMapper: NewsUseCaseMapper,
    private val newsMapperRepositoryModel: NewsMapperRepositoryModel,
) : NewsUseCase {

    /**
     * Flow containing the selected news, it will be listened by NewsDetailViewModel in order to show detail of current news
     */
    override val selectedNews: Flow<NewsItemUseCaseModel?> =
        newsEntity.selectedItem.map { newsItem ->
            newsUseCaseMapper.mapNewsEntiyToUseCase(newsItem)
        }

    /**
     * Flow containing the model received from service, it will be listened by viewmodel to define our list of items
     */
    override val itemsModel: Flow<NewsUseCaseStateModel> by lazy {
        newsEntity.itemsModel.map { itemsModel ->
            newsUseCaseMapper.mapNewsEntityToUseCase(itemsModel)
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getNews() {
        withContext(Dispatchers.IO)
        {
            // We get response from repository
            val news = newsRepository.getNews()

            // And then we update our entity, that will be listened by [itemsModel]
            newsEntity.onItemsLoaded(newsMapperRepositoryModel.mapNewsToEntity(repositoryModel = news))
        }
    }

    /**
     * When user selected a news we will call this method to update our entity, [selectedNews] will be triggered and used by our new fragment
     */
    override suspend fun updateSelectedNews(model: NewsItemUseCaseModel) {
        newsEntity.onItemSelected(newsUseCaseMapper.mapNewsItemUseCaseToEntity(model = model))
    }
}