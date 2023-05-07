package com.manuepi.fromscratchproject.entity

import com.manuepi.fromscratchproject.entity.model.NewsItemEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsStateEntityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NewsEntity {

    /**
     * Flow containing the selected item
     */
    val selectedItem: Flow<NewsItemEntityModel?>

    /**
     * Flow containing the list of items and infos
     */
    val itemsModel: StateFlow<NewsStateEntityModel>

    suspend fun onItemSelected(newItem: NewsItemEntityModel)
    suspend fun onItemsLoaded(itemsModel: NewsStateEntityModel)
}