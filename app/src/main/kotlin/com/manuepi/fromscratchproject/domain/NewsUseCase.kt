package com.manuepi.fromscratchproject.domain

import com.manuepi.fromscratchproject.domain.model.NewsItemUseCaseModel
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    val selectedNews: Flow<NewsItemUseCaseModel?>
    val itemsModel: Flow<NewsUseCaseStateModel>

    suspend fun getNews()
    suspend fun updateSelectedNews(model: NewsItemUseCaseModel)
}