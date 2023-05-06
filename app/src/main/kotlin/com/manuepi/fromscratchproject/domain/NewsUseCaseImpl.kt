package com.manuepi.fromscratchproject.domain

import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.domain.mapper.NewsUseCaseMapper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class NewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsUseCaseMapper: NewsUseCaseMapper,
    private val dispatcher: CoroutineDispatcher
) : NewsUseCase {
    override suspend fun getNews() =
        withContext(dispatcher)
        {
            val news = newsRepository.getNews()
            newsUseCaseMapper.mapNewsRepoToUseCase(news = news)
        }
}