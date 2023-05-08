package com.manuepi.fromscratchproject.datas

import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel

interface NewsRepository {
    suspend fun getNews(language: String): NewsRepositoryStateResponseModel
}