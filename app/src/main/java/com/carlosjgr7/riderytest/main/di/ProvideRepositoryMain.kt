package com.carlosjgr7.riderytest.main.di

import com.carlosjgr7.riderytest.main.data.datasource.MainRepositoryImplement
import com.carlosjgr7.riderytest.main.data.datasource.network.MainRemoteDataSource
import com.carlosjgr7.riderytest.main.data.repository.IMainRepository
import com.carlosjgr7.riderytest.main.domain.GetVenuesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {


    @Provides
    @Singleton
    fun provideMainRepository(dataSource: MainRemoteDataSource): IMainRepository  =
        MainRepositoryImplement(dataSource)

    @Provides
    @Singleton
    fun provideLoginUseCase(mainRepository: IMainRepository) = GetVenuesUseCase(mainRepository)
}

