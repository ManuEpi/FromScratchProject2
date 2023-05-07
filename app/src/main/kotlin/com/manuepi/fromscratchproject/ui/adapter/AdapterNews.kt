package com.manuepi.fromscratchproject.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.viewholder.NewsItemViewHolder

class AdapterNews : RecyclerView.Adapter<NewsItemViewHolder>() {

    var items: List<NewsItemUiModel> = emptyList()

    var onItemClicked: ((NewsItemUiModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder.newInstance(
            parent = parent,
            onClickListener = {
                onItemClicked?.invoke(it)
            }
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val item = this.items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size


}