package com.manuepi.fromscratchproject.entity.di

import com.manuepi.fromscratchproject.entity.NewsEntity
import com.manuepi.fromscratchproject.entity.impl.NewsEntityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency Injection for entity in order to include it as parameter
 */
@Module
@InstallIn(SingletonComponent::class)
class NewsItemEntityModule {
    @Provides
    @Singleton
    fun provideNewsItemEntityImpl(impl: NewsEntityImpl): NewsEntity = impl
}