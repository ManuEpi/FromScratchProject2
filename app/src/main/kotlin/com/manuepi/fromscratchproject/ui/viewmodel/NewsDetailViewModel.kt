package com.manuepi.fromscratchproject.ui.viewmodel

import androidx.lifecycle.*
import com.manuepi.fromscratchproject.domain.NewsUseCaseImpl
import com.manuepi.fromscratchproject.ui.mappers.NewsMapperUiModel
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsUseCaseImpl: NewsUseCaseImpl,
    private val newsMapperUiModel: NewsMapperUiModel
) : ViewModel() {

    val selectedNewsItem: LiveData<NewsItemUiModel?> by lazy {
        newsUseCaseImpl.selectedNews.map {
            newsMapperUiModel.mapUseCaseItemResponseToUi(item = it)
        }
            .asLiveData(Dispatchers.IO)
    }
}