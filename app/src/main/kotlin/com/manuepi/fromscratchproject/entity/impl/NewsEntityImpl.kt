package com.manuepi.fromscratchproject.entity.impl

import com.manuepi.fromscratchproject.entity.NewsEntity
import com.manuepi.fromscratchproject.entity.model.NewsItemEntityModel
import com.manuepi.fromscratchproject.entity.model.NewsStateEntityModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NewsEntityImpl @Inject constructor() : NewsEntity {
    internal val _selectedItem = MutableStateFlow<NewsItemEntityModel?>(null)
    override val selectedItem = _selectedItem.asStateFlow()

    internal val _itemsModel = MutableStateFlow<NewsStateEntityModel>(NewsStateEntityModel.NotSet)
    override val itemsModel = _itemsModel.asStateFlow()

    /**
     * Updating [_selectedItem] value
     */
    override suspend fun onItemSelected(newItem: NewsItemEntityModel) {
        _selectedItem.value = newItem
    }

    /**
     * Updating [_itemsModel] value
     */
    override suspend fun onItemsLoaded(itemsModel: NewsStateEntityModel) {
        _itemsModel.value = itemsModel
    }
}