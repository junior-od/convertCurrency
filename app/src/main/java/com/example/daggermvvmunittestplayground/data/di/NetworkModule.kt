package com.example.daggermvvmunittestplayground.data.di

import com.example.daggermvvmunittestplayground.data.apis.CurrencyApi
import com.example.daggermvvmunittestplayground.data.interceptor.RequestHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(clientOk: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientOk)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(requestHeaderInterceptor: RequestHeaderInterceptor): OkHttpClient {
        return OkHttpClient.Builder().followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(requestHeaderInterceptor)
            .build()
    }


}