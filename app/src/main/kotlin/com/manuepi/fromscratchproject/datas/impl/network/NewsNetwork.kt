package com.manuepi.fromscratchproject.datas.impl.network

import com.manuepi.fromscratchproject.datas.impl.api.models.NewsApiResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsNetwork {
    @GET("/v2/everything")
    suspend fun getNews(@Query("apiKey", encoded = true) apiKey: String): Response<NewsApiResponseModel>
}