package com.manuepi.fromscratchproject.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.manuepi.fromscratchproject.databinding.ActivityMainBinding
import com.manuepi.fromscratchproject.ui.adapter.AdapterNews
import com.manuepi.fromscratchproject.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


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
            adapterNews.onItemClicked = {
                //supportFragmentManager.beginTransaction()
                  //  .add(binding.root, DemoFragment(), "SOMETAG").commitAllowingStateLoss()
                viewModel.onItemClicked(item = it)
            }
        }
    }

    private fun initObservers() {
        viewModel.viewState.observe(this) { uiState ->
            Timber.e(uiState.state.toString())
            /*when (uiState.state)
            {
                NewsUiStateResponseModel.State.Failure -> TODO()
                NewsUiStateResponseModel.State.Init -> TODO()
                NewsUiStateResponseModel.State.Loading -> TODO()
                is NewsUiStateResponseModel.State.Success -> TODO()
            }*/
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}