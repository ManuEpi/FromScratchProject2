package com.manuepi.fromscratchproject.domainimpl

import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.datas.models.NewsItemRepositoryResponseModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryResponseModel
import com.manuepi.fromscratchproject.domain.NewsUseCaseImpl
import com.manuepi.fromscratchproject.domain.mapper.NewsUseCaseMapper
import com.manuepi.fromscratchproject.entity.NewsEntity
import io.mockk.coVerify
import com.manuepi.fromscratchproject.MockkRule
import com.google.common.truth.Truth
import com.manuepi.fromscratchproject.datas.impl.repository.mappers.NewsMapperRepositoryModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel
import com.manuepi.fromscratchproject.domain.model.NewsItemUseCaseModel
import com.manuepi.fromscratchproject.entity.impl.NewsEntityImpl
import com.manuepi.fromscratchproject.entity.model.NewsStateEntityModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test for useCase
 */
class NewsUseCaseTest {

    @get:Rule
    val mockKRule = MockkRule()

    lateinit var newsMapperRepositoryModel: NewsMapperRepositoryModel
    lateinit var newsUseCaseMapper: NewsUseCaseMapper

    lateinit var useCase: NewsUseCaseImpl

    @MockK(relaxed = true)
    private lateinit var repository: NewsRepository

    private lateinit var entity: NewsEntity

    @Before
    fun setUp() {
        entity = spyk(NewsEntityImpl())
        newsMapperRepositoryModel = NewsMapperRepositoryModel()
        newsUseCaseMapper = NewsUseCaseMapper()
        useCase = NewsUseCaseImpl(
            newsMapperRepositoryModel = newsMapperRepositoryModel,
            newsRepository = repository,
            newsEntity = entity,
            newsUseCaseMapper = newsUseCaseMapper,
        )
    }

    @Test
    fun `useCase should get news and update entity successfully`() = runBlocking {

        val data = NewsRepositoryResponseModel(
            status = "OK", totalResults = 2, articles = listOf(
                NewsItemRepositoryResponseModel(
                    source = null,
                    author = "author1",
                    title = "title1",
                    description = "description1",
                    url = "url1",
                    urlToImage = "urlToImage1",
                    publishedAt = "publishedAt1",
                    content = "content1"
                ),
                NewsItemRepositoryResponseModel(
                    source = null,
                    author = "author2",
                    title = "title2",
                    description = "description2",
                    url = "url2",
                    urlToImage = "urlToImage2",
                    publishedAt = "publishedAt2",
                    content = "content2"
                )
            )
        )

        coEvery { repository.getNews() } returns NewsRepositoryStateResponseModel.Success(
            model = data
        )

        useCase.getNews()

        coVerify(exactly = 1) {
            val itemsModel = newsMapperRepositoryModel.mapNewsToEntity(
                repositoryModel = NewsRepositoryStateResponseModel.Success(
                    model = data
                )
            )
            entity.onItemsLoaded(itemsModel)
        }

        Truth.assertThat(entity.itemsModel.value)
            .isInstanceOf(NewsStateEntityModel.Success::class.java)

        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.status)
            .isEqualTo(data.status)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.totalResults)
            .isEqualTo(data.totalResults)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.articles.size)
            .isEqualTo(data.articles.size)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.articles[0].author)
            .isEqualTo(data.articles[0].author)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.articles[0].title)
            .isEqualTo(data.articles[0].title)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.articles[0].description)
            .isEqualTo(data.articles[0].description)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.articles[0].content)
            .isEqualTo(data.articles[0].content)
    }

    @Test
    fun `useCase should not get news and update entity with failure`() = runBlocking {

        coEvery { repository.getNews() } returns NewsRepositoryStateResponseModel.Failure

        useCase.getNews()

        coVerify(exactly = 1) {
            val itemsModel = newsMapperRepositoryModel.mapNewsToEntity(
                repositoryModel = NewsRepositoryStateResponseModel.Failure
            )
            entity.onItemsLoaded(itemsModel)
        }

        Truth.assertThat(entity.itemsModel.value).isInstanceOf(NewsStateEntityModel.Failure::class.java)
    }

    @Test
    fun `useCase should update selected news item`() = runBlocking {

        val model = NewsItemUseCaseModel(
            source = null,
            author = "author1",
            title = "title1",
            description = "description1",
            url = "url1",
            urlToImage = "urlToImage1",
            publishedAt = "publishedAt1",
            content = "content1"
        )

        useCase.updateSelectedNews(model = model)

        coVerify {
            val item = newsUseCaseMapper.mapNewsItemUseCaseToEntity(
                model
            )
            entity.onItemSelected(item)
        }

        val entityValue = entity.selectedItem.first()

        Truth.assertThat(entityValue).isNotNull()
        Truth.assertThat(entityValue!!.title).isEqualTo(model.title)
        Truth.assertThat(entityValue.author).isEqualTo(model.author)
        Truth.assertThat(entityValue.description).isEqualTo(model.description)
        Truth.assertThat(entityValue.url).isEqualTo(model.url)
        Truth.assertThat(entityValue.urlToImage).isEqualTo(model.urlToImage)
        Truth.assertThat(entityValue.publishedAt).isEqualTo(model.publishedAt)
        Truth.assertThat(entityValue.content).isEqualTo(model.content)
    }
}