package com.example.daggermvvmunittestplayground.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggermvvmunittestplayground.commons.NetworkResource
import com.example.daggermvvmunittestplayground.data.models.Rates
import com.example.daggermvvmunittestplayground.utils.DispatcherProviders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProviders
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Error(val errorText: String): CurrencyEvent()
        object Loading: CurrencyEvent()
        object Empty: CurrencyEvent()
    }

    private val _convert = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val convert: StateFlow<CurrencyEvent> = _convert

    fun conversion(amount: String, fromCurrency: String, toCurrency: String) {
        if (amount == "0"){
            _convert.value = CurrencyEvent.Error("Amount cannot be zero")
            return
        }
        val floatAmount = amount.toFloatOrNull()
        if (floatAmount == null) {
            _convert.value = CurrencyEvent.Error("Amount is not valid")
            return
        }



        viewModelScope.launch(dispatchers.io) {
            _convert.value = CurrencyEvent.Loading
            when (val res = repository.getCurrencyRates(fromCurrency)) {
                is NetworkResource.Error -> {
                    res.message?.let {
                        _convert.value = CurrencyEvent.Error(it)
                    }

                }
                is NetworkResource.Success -> {
                    res.data?.let {
                        val rates = it.rates
                        val rate = getRateForCurrency(toCurrency, rates)

                        if (rate == null) {
                            _convert.value = CurrencyEvent.Error("Unexpected Error")
                        } else {
                            val convertedValue = (floatAmount * rate)

                            _convert.value = CurrencyEvent.Success(
                                "$amount $fromCurrency = $convertedValue $toCurrency"
                            )
                        }
                    }

                }
            }
        }

    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "AED" -> rates.AED
        "CAD" -> rates.CAD
//        "HKD" -> rates.HKD
        "ISK" -> rates.ISK
        "EUR" -> rates.EUR
//        "PHP" -> rates.PHP
        "DKK" -> rates.DKK
        "HUF" -> rates.HUF
        "CZK" -> rates.CZK
        "AUD" -> rates.AUD
//        "RON" -> rates.RON
//        "SEK" -> rates.SEK
        "IDR" -> rates.IDR
        "INR" -> rates.INR
        "BRL" -> rates.BRL
//        "RUB" -> rates.RUB
        "HRK" -> rates.HRK
        "JPY" -> rates.JPY
//        "THB" -> rates.THB
        "CHF" -> rates.CHF
//        "SGD" -> rates.SGD
//        "PLN" -> rates.PLN
        "BGN" -> rates.BGN
        "CNY" -> rates.CNY
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
//        "ZAR" -> rates.ZAR
//        "USD" -> rates.USD
        "MXN" -> rates.MXN
        "ILS" -> rates.ILS
        "GBP" -> rates.GBP
        "KRW" -> rates.KRW
        "MYR" -> rates.MYR
        else -> null
    }
}