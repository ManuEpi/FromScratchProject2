package com.manuepi.fromscratchproject.ui

import MockkRule
import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.datas.impl.repository.mappers.NewsMapperRepositoryModel
import com.manuepi.fromscratchproject.domain.NewsUseCaseImpl
import com.manuepi.fromscratchproject.domain.mapper.NewsUseCaseMapper
import com.manuepi.fromscratchproject.entity.NewsEntity
import com.manuepi.fromscratchproject.entity.impl.NewsEntityImpl
import com.manuepi.fromscratchproject.ui.mappers.NewsMapperUiModel
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.viewmodel.MainActivityViewModel
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test for UI
 */
class MainActivityViewModelTest {

    @get:Rule
    val mockKRule = MockkRule()

    lateinit var viewModel: MainActivityViewModel

    lateinit var newsMapperRepositoryModel: NewsMapperRepositoryModel
    lateinit var newsUseCaseMapper: NewsUseCaseMapper
    lateinit var newsMapperUiModel: NewsMapperUiModel

    @MockK(relaxed = true)
    lateinit var useCase: NewsUseCaseImpl

    private lateinit var entity: NewsEntity

    @Before
    fun setUp() {
        entity = spyk(NewsEntityImpl())
        newsMapperRepositoryModel = NewsMapperRepositoryModel()
        newsUseCaseMapper = NewsUseCaseMapper()
        newsMapperUiModel = NewsMapperUiModel()

        viewModel = MainActivityViewModel(
            newsUseCase = useCase, newsMapperUiModel = NewsMapperUiModel()
        )
    }

    @Test
    fun `viewModel getNews should call usecase getNews method`() = runBlocking {

        viewModel.getNews()

        coVerify(exactly = 1) { useCase.getNews() }
    }

    @Test
    fun `viewModel updateSelectedNews should call usecase updateSelectedNews method`() =
        runBlocking {

            val item = NewsItemUiModel(
                source = null,
                author = "author1",
                title = "title1",
                description = "description1",
                url = "url1",
                urlToImage = "urlToImage1",
                publishedAt = "publishedAt1",
                content = "content1"
            )

            viewModel.updateSelectedNews(
                newsItem = item
            )

            coVerify(exactly = 1) {
                val mapped = newsMapperUiModel.mapUiToUseCase(uiModel = item)
                useCase.updateSelectedNews(
                    model = mapped
                )
            }
        }
}