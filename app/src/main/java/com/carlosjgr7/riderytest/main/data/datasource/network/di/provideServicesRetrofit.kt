package com.carlosjgr7.riderytest.main.data.datasource.network.di

import com.carlosjgr7.riderytest.core.di.network.NetworkModule
import com.carlosjgr7.riderytest.main.data.datasource.network.IMainServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object LoginServiceModule {

    @Provides
    fun provideServicesRetrofit(retrofit: Retrofit): IMainServices {
        return retrofit.create(IMainServices::class.java)
    }
}