package com.manuepi.fromscratchproject.entity.impl

import com.manuepi.fromscratchproject.entity.NewsEntity
import com.manuepi.fromscratchproject.entity.model.NewsItemEntityModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NewsEntityImpl @Inject constructor() : NewsEntity {
    internal val _selectedItem = MutableStateFlow<NewsItemEntityModel?>(null)
    override val selectedItem = _selectedItem.asStateFlow()

    override suspend fun onItemSelected(newItem: NewsItemEntityModel) {
        _selectedItem.value = newItem
    }
}