package com.manuepi.fromscratchproject.entity

import com.google.common.truth.Truth
import com.manuepi.fromscratchproject.entity.impl.NewsEntityImpl
import com.manuepi.fromscratchproject.entity.model.NewsEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsItemEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsStateEntityModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Unit test for entity
 */
class NewsEntityTest {

    lateinit var entity: NewsEntityImpl

    @Before
    fun setUp() {
        entity = NewsEntityImpl()
    }

    @Test
    fun `onItemSelected should update selected item in entity`() = runBlocking {

        val newItem = NewsItemEntityModel(
            source = null,
            author = "author",
            title = "title",
            description = "description",
            url = "url",
            urlToImage = "urlToImage",
            publishedAt = "publishedAt",
            content = "content"
        )


        entity.onItemSelected(
            newItem = newItem
        )

        Truth.assertThat(entity.selectedItem.value).isNotNull()
        Truth.assertThat(entity.selectedItem.value!!.author).isEqualTo(newItem.author)
        Truth.assertThat(entity.selectedItem.value!!.title).isEqualTo(newItem.title)
        Truth.assertThat(entity.selectedItem.value!!.description).isEqualTo(newItem.description)
        Truth.assertThat(entity.selectedItem.value!!.url).isEqualTo(newItem.url)
        Truth.assertThat(entity.selectedItem.value!!.urlToImage).isEqualTo(newItem.urlToImage)
    }

    @Test
    fun `onItemsLoaded should update news item model in entity`() = runBlocking {

        val itemsModel = NewsStateEntityModel.Success(
            model = NewsEntityModel(
                status = "OK",
                totalResults = 2,
                articles = listOf(
                    NewsItemEntityModel(
                        source = null,
                        author = "author1",
                        title = "title1",
                        description = "description1",
                        url = "url1",
                        urlToImage = "urlToImage1",
                        publishedAt = "publishedAt1",
                        content = "content1"
                    ),
                    NewsItemEntityModel(
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
        )


        entity.onItemsLoaded(
            itemsModel = itemsModel
        )

        Truth.assertThat(entity.itemsModel.value)
            .isInstanceOf(NewsStateEntityModel.Success::class.java)

        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.status)
            .isEqualTo(itemsModel.model.status)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.totalResults)
            .isEqualTo(itemsModel.model.totalResults)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.articles.size)
            .isEqualTo(itemsModel.model.articles.size)
        Truth.assertThat((entity.itemsModel.value as NewsStateEntityModel.Success).model.articles)
            .isEqualTo(itemsModel.model.articles)
    }
}