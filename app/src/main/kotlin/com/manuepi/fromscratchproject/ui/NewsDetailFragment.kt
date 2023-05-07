package com.manuepi.fromscratchproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.manuepi.fromscratchproject.R
import com.manuepi.fromscratchproject.databinding.NewsDetailFragmentBinding
import com.manuepi.fromscratchproject.ui.models.NewsItemUiModel
import com.manuepi.fromscratchproject.ui.viewmodel.NewsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsDetailFragment @Inject constructor() : Fragment() {
    companion object {
        fun newInstance() = NewsDetailFragment()
    }

    private var _binding: NewsDetailFragmentBinding? = null
    private val binding: NewsDetailFragmentBinding
        get() = _binding!!

    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.supportFragmentManager?.popBackStack()
                }
            })

        return NewsDetailFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    fun initObservers() {
        viewModel.selectedNewsItem.observe(viewLifecycleOwner) { newItem ->
            if (newItem != null) {
                updateUi(newItem = newItem)
            }
        }
    }

    fun updateUi(newItem: NewsItemUiModel) {

        newItem.urlToImage?.let { url ->
            val imgUri = url.toUri().buildUpon().scheme("https").build()
            binding.fragmentNewsDetailImage.load(imgUri)
        } ?: binding.fragmentNewsDetailImage.setImageResource(R.drawable.ic_launcher_foreground)

        binding.fragmentNewsDetailTitle.apply {
            text = newItem.title
            contentDescription = newItem.title
        }

        if (!newItem.author.isNullOrEmpty()) {
            binding.fragmentNewsDetailSubtitle.apply {
                text = "Auteur: ${newItem.author}"
                contentDescription = newItem.author
            }
        } else
            binding.fragmentNewsDetailSubtitle.visibility = View.GONE


        if (!newItem.publishedAt.isNullOrEmpty()) {
            binding.fragmentNewsDetailSubtitle2.apply {
                text = "Date: ${newItem.publishedAt}"
                contentDescription = newItem.publishedAt
            }
        } else
            binding.fragmentNewsDetailSubtitle2.visibility = View.GONE

        if (!newItem.content.isNullOrEmpty()) {
            binding.fragmentNewsDetailContent.apply {
                text = "Contenu: ${newItem.content}"
                contentDescription = newItem.content
            }
        } else
            binding.fragmentNewsDetailContent.visibility = View.GONE

        if (!newItem.url.isNullOrEmpty()) {
            binding.fragmentNewsDetailLink.apply {
                text = "Lien: ${newItem.url}"
                contentDescription = newItem.url
            }
        } else
            binding.fragmentNewsDetailLink.visibility = View.GONE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}