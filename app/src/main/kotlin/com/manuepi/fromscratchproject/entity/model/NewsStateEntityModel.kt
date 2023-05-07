package com.manuepi.fromscratchproject.entity.model

sealed class NewsStateEntityModel {
    object NotSet : NewsStateEntityModel()

    data class Success(
        val model: NewsEntityModel
    ) : NewsStateEntityModel()

    object Failure : NewsStateEntityModel()
}