package com.manuepi.fromscratchproject.datas.impl.api

import com.manuepi.fromscratchproject.NetworkResponse
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsApiResponseModel

interface NewsApi {
    // get news from Service
    suspend fun getNews(language: String): NetworkResponse<NewsApiResponseModel>
}