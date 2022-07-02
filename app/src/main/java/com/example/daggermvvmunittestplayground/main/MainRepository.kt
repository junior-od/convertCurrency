package com.example.daggermvvmunittestplayground.main

import com.example.daggermvvmunittestplayground.commons.NetworkResource
import com.example.daggermvvmunittestplayground.data.models.CurrencyResponse

interface MainRepository {  //reason so it's easier to test view models with it

    suspend fun getCurrencyRates(base: String): NetworkResource<CurrencyResponse>
}