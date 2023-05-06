package com.manuepi.fromscratchproject.datas.models

sealed class NewsRepositoryStateResponseModel {
    data class Success(
        val model: NewsRepositoryResponseModel
    ) : NewsRepositoryStateResponseModel()

    object Failure : NewsRepositoryStateResponseModel()
}