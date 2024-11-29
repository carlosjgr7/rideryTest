package com.carlosjgr7.riderytest.core.di.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Named("addHeadersInterceptor")
    fun provideAddHeadersInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest =
                chain.request()
                    .newBuilder()
                    .apply {
                        runBlocking {
                            addHeader("accept", "application/json")
                        }
                    }.build()

            chain.proceed(newRequest)
        }
    }

    @Provides
    @Named("chuckerLoggingInterceptor")
    fun provideChuckerLoggingInterceptor(
        @ApplicationContext context: Context,
    ): Interceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }


    @Provides
    fun provideAuthHttpClient(
        @Named("addHeadersInterceptor") addHeadersInterceptor: Interceptor,
        @Named("chuckerLoggingInterceptor") chuckerInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(addHeadersInterceptor)
            .build()
    }


    @Provides
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    fun provideRetrofit(
        json: Json,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }
}
