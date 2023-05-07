package com.manuepi.fromscratchproject.domain.model

sealed class NewsUseCaseStateModel {

    object NotSet: NewsUseCaseStateModel()
    data class Success(
        val model: NewsUseCaseModel
    ) : NewsUseCaseStateModel()

    object Failure : NewsUseCaseStateModel()
}