package com.manuepi.fromscratchproject.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.manuepi.fromscratchproject.R
import com.manuepi.fromscratchproject.databinding.NewsListItemBinding
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel

class NewsItemViewHolder(
    val viewBinding: NewsListItemBinding,
    val onClickListener: ((NewsItemUiModel) -> Unit)?
) : RecyclerView.ViewHolder(viewBinding.root) {
    var item: NewsItemUiModel? = null

    companion object {
        fun newInstance(
            parent: ViewGroup,
            onClickListener: (NewsItemUiModel) -> Unit
        ) =
            NewsItemViewHolder(
                viewBinding = NewsListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onClickListener = onClickListener
            )
    }

    init {
        viewBinding.root.setOnClickListener {
            item?.let {
                onClickListener?.invoke(it)
            }
        }
    }

    fun bind(adapterItem: NewsItemUiModel) {
        this.item = adapterItem

        adapterItem.urlToImage?.let { url ->
            val imgUri = url.toUri().buildUpon().scheme("https").build()
            viewBinding.newsItemImage.load(imgUri)
        } ?: viewBinding.newsItemImage.setImageResource(R.drawable.ic_launcher_foreground)

        viewBinding.newsItemTitle.apply {
            text = adapterItem.title
            contentDescription = adapterItem.title
        }

        viewBinding.newsItemSubtitle.apply {
            text = adapterItem.description
            contentDescription = adapterItem.description
        }
    }
}