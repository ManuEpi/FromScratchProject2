package com.manuepi.fromscratchproject.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.viewholder.NewsItemViewHolder
import java.lang.IllegalArgumentException

class AdapterNews : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<NewsItemUiModel> = emptyList()
        set(value) {
            field = value
        }

    var onItemClicked: ((NewsItemUiModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsItemViewHolder.newInstance(
            parent = parent,
            onClickListener = {
                onItemClicked?.invoke(it)
            }
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = this.items[position]
        val isLastItem = position == itemCount - 1
        when (holder) {
            is NewsItemViewHolder -> {
                holder.bind(item, isLastItem)
            }
            else -> {
                throw IllegalArgumentException("onBindViewHolder holder not typed")
            }
        }
    }

}