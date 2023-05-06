package com.manuepi.fromscratchproject.datas.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Provides
    @Singleton
    fun provideCoroutineAppScope(
        dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @Named(DispatchersNames.UI_LAYOUT)
    @Singleton
    fun provideUiLayoutDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named(DispatchersNames.UI_VIEW_MODEL)
    @Singleton
    fun provideUiViewModelDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named(DispatchersNames.DOMAIN)
    @Singleton
    fun provideDomainDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named(DispatchersNames.DATA)
    @Singleton
    fun provideDataDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named(DispatchersNames.ENTITY)
    @Singleton
    fun provideEntityDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Named(ScopesName.PROFILE_SESSION_SCOPE)
    @Singleton
    fun provideCoroutineProfileScope(
        @Named(DispatchersNames.DOMAIN) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}