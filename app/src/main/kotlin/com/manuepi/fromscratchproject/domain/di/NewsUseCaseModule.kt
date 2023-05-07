package com.manuepi.fromscratchproject.domain.di

import com.manuepi.fromscratchproject.domain.NewsUseCase
import com.manuepi.fromscratchproject.domain.NewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Dependency Injection for usecase in order to include it as parameter
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NewsUseCaseModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindsNewsUseCase(impl: NewsUseCaseImpl): NewsUseCase
}