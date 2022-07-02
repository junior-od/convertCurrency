package com.example.daggermvvmunittestplayground.main

import com.example.daggermvvmunittestplayground.commons.NetworkResource
import com.example.daggermvvmunittestplayground.data.apis.CurrencyApi
import com.example.daggermvvmunittestplayground.data.models.CurrencyResponse
import javax.inject.Inject

class DefaultMainRepository @Inject constructor( private val currencyApi: CurrencyApi): MainRepository {
    override suspend fun getCurrencyRates(base: String): NetworkResource<CurrencyResponse> {
        return try {
            val response = currencyApi.getCurrencyRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkResource.Success(result)
            } else {
                NetworkResource.Error("Error Occurred")
            }

        } catch (e: Exception) {
            NetworkResource.Error(e.message ?: "An Error Occurred")
        }
    }
}