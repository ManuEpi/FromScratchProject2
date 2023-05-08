package com.manuepi.fromscratchproject.dataimpl

import MockkRule
import com.google.common.truth.Truth
import com.manuepi.fromscratchproject.NetworkResponse
import com.manuepi.fromscratchproject.datas.impl.api.NewsApi
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsApiResponseModel
import com.manuepi.fromscratchproject.datas.impl.api.models.NewsItemApiResponseModel
import com.manuepi.fromscratchproject.datas.impl.repository.NewsRepositoryImpl
import com.manuepi.fromscratchproject.datas.impl.repository.mappers.NewsMapperRepositoryModel
import com.manuepi.fromscratchproject.datas.models.NewsRepositoryStateResponseModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test for repository
 */
class NewsRepositoryTest {

    @get:Rule
    val mockKRule = MockkRule()

    lateinit var repository: NewsRepositoryImpl

    @MockK(relaxed = true)
    private lateinit var api: NewsApi

    @Before
    fun setUp() {
        repository = NewsRepositoryImpl(
            newsApi = api,
            newsMapperRepositoryModel = NewsMapperRepositoryModel()
        )
    }

    @Test
    fun `getNews should get news from service with success`() = runBlocking {

        val data = NewsApiResponseModel(
            status = "OK", totalResults = 2, articles = listOf(
                NewsItemApiResponseModel(
                    source = null,
                    author = "author1",
                    title = "title1",
                    description = "description1",
                    url = "url1",
                    urlToImage = "urlToImage1",
                    publishedAt = "publishedAt1",
                    content = "content1"
                ),
                NewsItemApiResponseModel(
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

        coEvery { api.getNews() } returns NetworkResponse.Success(
            data = data
        )

        val response = repository.getNews()

        Truth.assertThat(response).isInstanceOf(NewsRepositoryStateResponseModel.Success::class.java)
        Truth.assertThat((response as NewsRepositoryStateResponseModel.Success).model.status).isEqualTo(data.status)
        Truth.assertThat(response.model.totalResults).isEqualTo(data.totalResults)
        Truth.assertThat(response.model.articles.size).isEqualTo(data.articles.size)
        Truth.assertThat(response.model.articles[0].author).isEqualTo(data.articles[0].author)
        Truth.assertThat(response.model.articles[0].title).isEqualTo(data.articles[0].title)
        Truth.assertThat(response.model.articles[0].description).isEqualTo(data.articles[0].description)
        Truth.assertThat(response.model.articles[0].content).isEqualTo(data.articles[0].content)
    }

    @Test
    fun `getNews should get news from service with error`() = runBlocking {

        coEvery { api.getNews() } returns NetworkResponse.Error(message = "Une erreur est survenue")

        val response = repository.getNews()

        Truth.assertThat(response)
            .isInstanceOf(NewsRepositoryStateResponseModel.Failure::class.java)
    }
}