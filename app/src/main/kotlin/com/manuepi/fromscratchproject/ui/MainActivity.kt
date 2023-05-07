package com.manuepi.fromscratchproject.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.manuepi.fromscratchproject.R
import com.manuepi.fromscratchproject.databinding.ActivityMainBinding
import com.manuepi.fromscratchproject.ui.adapter.AdapterNews
import com.manuepi.fromscratchproject.ui.models.NewsUiStateResponseModel
import com.manuepi.fromscratchproject.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainActivityViewModel>()

    private val adapterNews = AdapterNews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initRecyclerview()
        initObservers()
    }

    private fun initRecyclerview() {
        binding.activityMainRecyclerview.let { recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapterNews
            adapterNews.onItemClicked = { newsItem ->
                viewModel.updateSelectedNews(newsItem)
                createNewFragment()
            }
        }
    }

    private fun createNewFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_main_container, NewsDetailFragment())
            .addToBackStack("newsDetail")
            .commit()
    }

    private fun initObservers() {
        viewModel.viewState.observe(this) { uiState ->
            when (val state = uiState.state) {
                NewsUiStateResponseModel.State.Failure -> {
                    // no-op
                }
                NewsUiStateResponseModel.State.Init -> {
                    // no-op
                }
                NewsUiStateResponseModel.State.Loading -> {
                    // no-op
                }
                is NewsUiStateResponseModel.State.Success -> {
                    binding.activityMainTitle.apply {
                        text =
                            "Nous vous avons trouvé ${state.model.totalResults ?: 0} résultats pour le mot bitcoin"
                        contentDescription =
                            "Nous vous avons trouvé ${state.model.totalResults ?: 0} résultats pour le mot bitcoin"
                    }
                    adapterNews.items = state.model.articles
                }
            }
        }
    }

    override fun onDestroy() {
        binding.activityMainRecyclerview.adapter = null
        _binding = null
        adapterNews.onItemClicked = null
        super.onDestroy()
    }
}