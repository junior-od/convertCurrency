package com.example.daggermvvmunittestplayground

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.daggermvvmunittestplayground.databinding.ActivityMainBinding
import com.example.daggermvvmunittestplayground.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.convertButton.setOnClickListener {
            viewModel.conversion(
                binding.amount.text.toString(),
                binding.fromCurrencySpinner.selectedItem.toString(),
                binding.toCurrencySpinner.selectedItem.toString(),
            )
        }

        //launch in lifecycle scope
        lifecycleScope.launchWhenStarted {
            viewModel.convert.collect { event ->
                when (event) {
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressResult.visibility = View.VISIBLE
                        binding.conversionResult.visibility = View.GONE

                    }

                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressResult.visibility = View.GONE
                        //result of conversion
                        binding.conversionResult.text = event.resultText
                        binding.conversionResult.visibility = View.VISIBLE

                    }

                    is MainViewModel.CurrencyEvent.Error -> {
                        binding.progressResult.visibility = View.GONE
                        //error
                        binding.conversionResult.text = event.errorText
                        binding.conversionResult.visibility = View.VISIBLE

                    }
                    else -> Unit
                }
            }
        }
    }
}