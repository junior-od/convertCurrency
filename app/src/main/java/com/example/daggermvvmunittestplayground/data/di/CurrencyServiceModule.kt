package com.example.daggermvvmunittestplayground.data.di

import com.example.daggermvvmunittestplayground.data.apis.CurrencyApi
import com.example.daggermvvmunittestplayground.main.DefaultMainRepository
import com.example.daggermvvmunittestplayground.main.MainRepository
import com.example.daggermvvmunittestplayground.utils.DispatcherProviders
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyServiceModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(retrofit: Retrofit) : CurrencyApi =
        retrofit.create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: CurrencyApi): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers() : DispatcherProviders = object: DispatcherProviders {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}