package com.manuepi.fromscratchproject.datas.impl.api

import com.manuepi.fromscratchproject.NetworkResponse
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsApiResponseModel
import com.manuepi.fromscratchproject.datas.impl.network.NewsNetwork
import com.manuepi.fromscratchproject.safeApiCall
import javax.inject.Inject

class NewsApiImpl @Inject constructor(
    private val newsNetwork: NewsNetwork
) : NewsApi {

    companion object {
        const val apiKey = "d23afaf5af8244acb3b5e4d2bc63fbe6"
    }

    override suspend fun getNews(language: String): NetworkResponse<NewsApiResponseModel> =
        // Handle service response for getNews call
        safeApiCall {
            newsNetwork.getNews(apiKey = apiKey, q = "bitcoin", language = language)
        }
}