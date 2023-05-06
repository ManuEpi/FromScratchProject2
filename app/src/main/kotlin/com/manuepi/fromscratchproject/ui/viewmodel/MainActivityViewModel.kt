package com.manuepi.fromscratchproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuepi.fromscratchproject.asLiveData
import com.manuepi.fromscratchproject.domain.NewsUseCaseImpl
import com.manuepi.fromscratchproject.domain.model.NewsUseCaseStateModel
import com.manuepi.fromscratchproject.setState
import com.manuepi.fromscratchproject.ui.mappers.NewsMapperUiModel
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.models.NewsUiStateResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val newsUseCaseImpl: NewsUseCaseImpl,
    private val newsMapperUiModel: NewsMapperUiModel
) : ViewModel() {

    private val _viewState = MutableLiveData(NewsUiStateResponseModel())
    val viewState: LiveData<NewsUiStateResponseModel> by lazy {
        viewModelScope.launch(dispatcher) {
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

    fun onItemClicked(item: NewsItemUiModel)
    {

    }
}