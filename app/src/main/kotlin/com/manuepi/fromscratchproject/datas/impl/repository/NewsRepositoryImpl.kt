package com.manuepi.fromscratchproject.datas.impl.repository

import com.manuepi.fromscratchproject.datas.di.DispatchersNames
import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.datas.impl.api.NewsApi
import com.manuepi.fromscratchproject.datas.impl.repository.mappers.NewsMapperRepositoryModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsMapperRepositoryModel: NewsMapperRepositoryModel,
    @Named(DispatchersNames.DATA) private val dispatcher: CoroutineDispatcher
): NewsRepository {
    override suspend fun getNews(): NewsRepositoryStateResponseModel {
        return withContext(dispatcher)
        {
            val response = newsApi.getNews()
            newsMapperRepositoryModel.mapNews(response)
        }
    }
}