package com.example.daggermvvmunittestplayground.data.interceptor

import com.example.daggermvvmunittestplayground.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestHeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain
            .proceed(
                chain
                    .request()
                    .newBuilder()
                    .addHeader("apikey", BuildConfig.API_KEY)
                    .build()
            )
    }

}