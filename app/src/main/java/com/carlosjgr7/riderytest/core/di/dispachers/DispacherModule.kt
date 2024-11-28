package com.carlosjgr7.riderytest.core.di.dispachers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule{

    @IoDispatcher
    @Provides
    fun provideDispatcherIo(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main
}

@Retention(AnnotationRetention. BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention. BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention. BINARY)
@Qualifier
annotation class MainDispatcher