package com.manuepi.fromscratchproject.ui.viewmodel

import androidx.lifecycle.*
import com.manuepi.fromscratchproject.domain.NewsUseCase
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel
import com.manuepi.fromscratchproject.ui.mappers.NewsMapperUiModel
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.models.NewsUiStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val newsMapperUiModel: NewsMapperUiModel
) : ViewModel() {

    val viewState: LiveData<NewsUiStateModel.State> =
        newsUseCase.itemsModel.flowOn(Dispatchers.IO).map {
            when (it) {
                NewsUseCaseStateModel.Failure -> {
                    NewsUiStateModel.State.Failure
                }
                NewsUseCaseStateModel.NotSet -> {
                    NewsUiStateModel.State.Loading
                }
                is NewsUseCaseStateModel.Success -> {
                    NewsUiStateModel.State.Success(
                        model = newsMapperUiModel.mapUseCaseResponseToUi(
                            response = it.model
                        )
                    )
                }
            }
        }.asLiveData(Dispatchers.IO)

    /*private val _viewState = MutableLiveData(NewsUiStateModel())
    val viewState: LiveData<NewsUiStateModel> by lazy {
        viewModelScope.launch(Dispatchers.IO) {
            newsUseCase.itemsModel.map {
                when (val state = it) {
                    NewsUseCaseStateModel.Failure -> {
                        _viewState.setState { actualViewState ->
                            actualViewState.copy(
                                state = NewsUiStateModel.State.Failure
                            )
                        }
                    }
                    is NewsUseCaseStateModel.Success -> {
                        _viewState.setState { actualViewState ->
                            actualViewState.copy(
                                state = NewsUiStateModel.State.Success(
                                    model = newsMapperUiModel.mapUseCaseResponseToUi(
                                        response = state.model
                                    )
                                )
                            )
                        }
                    }
                    NewsUseCaseStateModel.NotSet -> {
                        _viewState.setState { actualViewState ->
                            actualViewState.copy(
                                state = NewsUiStateModel.State.Loading
                            )
                        }
                    }
                }
            }
        }
        _viewState.asLiveData()
    }*/

    fun onResume() {
        viewModelScope.launch(Dispatchers.IO) {
            newsUseCase.getNews()
        }
    }

    fun updateSelectedNews(newsItem: NewsItemUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            newsUseCase.updateSelectedNews(newsMapperUiModel.mapUiToUseCase(uiModel = newsItem))
        }
    }
}