package com.example.daggermvvmunittestplayground.data.apis

import com.example.daggermvvmunittestplayground.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface CurrencyApi {
    @GET("latest")
    suspend fun getCurrencyRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>
}