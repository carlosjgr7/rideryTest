package com.carlosjgr7.riderytest.login.di

import com.carlosjgr7.riderytest.login.data.datasource.LoginRepositoryImplement
import com.carlosjgr7.riderytest.login.data.datasource.local.LoginLocalDataSource
import com.carlosjgr7.riderytest.login.data.datasource.network.LoginRemoteDataSource
import com.carlosjgr7.riderytest.login.data.repository.ILoginRepository
import com.carlosjgr7.riderytest.login.domain.LoginWithGoogleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        loginRemoteDataSource: LoginRemoteDataSource,
        loginLocalDataSource: LoginLocalDataSource
    ): ILoginRepository {
        return LoginRepositoryImplement(loginRemoteDataSource, loginLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: ILoginRepository) =
        LoginWithGoogleUseCase(loginRepository)
}