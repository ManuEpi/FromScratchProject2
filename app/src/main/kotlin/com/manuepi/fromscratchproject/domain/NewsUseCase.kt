package com.manuepi.fromscratchproject.domain

import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel

interface NewsUseCase {

    suspend fun getNews(): NewsUseCaseStateModel
}