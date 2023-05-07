package com.manuepi.fromscratchproject.entity

import com.manuepi.fromscratchproject.entity.model.NewsItemEntityModel
import kotlinx.coroutines.flow.Flow

interface NewsEntity {

    /**
     * Flow containing the selected item
     */
    val selectedItem: Flow<NewsItemEntityModel?>

    suspend fun onItemSelected(newItem: NewsItemEntityModel)
}