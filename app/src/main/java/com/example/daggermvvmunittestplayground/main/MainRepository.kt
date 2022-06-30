package com.example.daggermvvmunittestplayground.main

import com.example.daggermvvmunittestplayground.commons.NetworkResource
import com.example.daggermvvmunittestplayground.data.models.CurrencyResponse

interface MainRepository {

    suspend fun getCurrencyRates(base: String): NetworkResource<CurrencyResponse>
}