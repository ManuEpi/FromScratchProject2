package com.manuepi.fromscratchproject.datas.impl.api

import com.manuepi.fromscratchproject.NetworkResponse
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsApiResponseModel

interface NewsApi {
    suspend fun getNews(): NetworkResponse<NewsApiResponseModel>
}