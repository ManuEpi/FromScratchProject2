package com.manuepi.fromscratchproject.domain.model

sealed class NewsUseCaseStateModel {
    data class Success(
        val model: NewsUseCaseModel
    ) : NewsUseCaseStateModel()

    object Failure : NewsUseCaseStateModel()
}