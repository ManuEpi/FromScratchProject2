package com.manuepi.fromscratchproject.domain

import com.manuepi.fromscratchproject.datas.di.DispatchersNames
import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.domain.mapper.NewsUseCaseMapper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class NewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsUseCaseMapper: NewsUseCaseMapper,
    @Named(DispatchersNames.DOMAIN) private val dispatcher: CoroutineDispatcher
) : NewsUseCase {
    override suspend fun getNews() =
        withContext(dispatcher)
        {
            val news = newsRepository.getNews()
            newsUseCaseMapper.mapNewsRepoToUseCase(news = news)
        }
}