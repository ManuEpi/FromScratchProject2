package com.manuepi.fromscratchproject.datas.di

import com.manuepi.fromscratchproject.datas.NewsRepository
import com.manuepi.fromscratchproject.datas.impl.api.NewsApi
import com.manuepi.fromscratchproject.datas.impl.api.NewsApiImpl
import com.manuepi.fromscratchproject.datas.impl.network.NewsNetwork
import com.manuepi.fromscratchproject.datas.impl.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
class NewsRepositoryModule {

    private val BASE_URL = "https://newsapi.org"

    @ActivityRetainedScoped
    @Provides
    fun providesNewsApi(impl: NewsApiImpl): NewsApi = impl

    @ActivityRetainedScoped
    @Provides
    fun providesNewsRepository(impl: NewsRepositoryImpl): NewsRepository = impl

    @ActivityRetainedScoped
    @Provides
    fun providesNewsNetwork(): NewsNetwork {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsNetwork::class.java)
    }
}