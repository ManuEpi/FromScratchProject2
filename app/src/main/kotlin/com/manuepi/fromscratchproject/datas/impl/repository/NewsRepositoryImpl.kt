package com.manuepi.fromscratchproject.datas.impl.repository

import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.datas.impl.api.NewsApi
import com.manuepi.fromscratchproject.datas.impl.repository.mappers.NewsMapperRepositoryModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsMapperRepositoryModel: NewsMapperRepositoryModel
) : NewsRepository {
    override suspend fun getNews(): NewsRepositoryStateResponseModel {
        val response = newsApi.getNews()
        return newsMapperRepositoryModel.mapNewsToRepository(response)
    }
}