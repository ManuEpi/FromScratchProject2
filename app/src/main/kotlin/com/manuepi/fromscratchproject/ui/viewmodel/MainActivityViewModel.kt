package com.manuepi.fromscratchproject.ui.viewmodel

import androidx.lifecycle.*
import com.manuepi.fromscratchproject.asLiveData
import com.manuepi.fromscratchproject.domain.NewsUseCaseImpl
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel
import com.manuepi.fromscratchproject.setState
import com.manuepi.fromscratchproject.ui.mappers.NewsMapperUiModel
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.models.NewsUiStateResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val newsUseCaseImpl: NewsUseCaseImpl,
    private val newsMapperUiModel: NewsMapperUiModel
) : ViewModel() {

    private val _viewState = MutableLiveData(NewsUiStateResponseModel())
    val viewState: LiveData<NewsUiStateResponseModel> by lazy {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.setState { actualViewState ->
                actualViewState.copy(
                    state = NewsUiStateResponseModel.State.Loading
                )
            }

            when (val response = newsUseCaseImpl.getNews()) {
                NewsUseCaseStateModel.Failure -> {
                    _viewState.setState { actualViewState ->
                        actualViewState.copy(
                            state = NewsUiStateResponseModel.State.Failure
                        )
                    }
                }

                is NewsUseCaseStateModel.Success -> {
                    _viewState.setState { actualViewState ->
                        actualViewState.copy(
                            state = NewsUiStateResponseModel.State.Success(
                                model = newsMapperUiModel.mapUseCaseResponseToUi(
                                    response = response.model
                                )
                            )
                        )
                    }
                }
            }
        }

        _viewState.asLiveData()
    }

    fun updateSelectedNews(newsItem: NewsItemUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            newsUseCaseImpl.updateSelectedNews(newsMapperUiModel.mapUiToUseCase(uiModel = newsItem))
        }
    }
}