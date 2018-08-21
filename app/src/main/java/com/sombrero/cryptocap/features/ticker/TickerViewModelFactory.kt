package com.sombrero.cryptocap.features.ticker

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sombrero.cryptomodel.ticker.TickerCoinRepository

class TickerViewModelFactory(private val repository: TickerCoinRepository)
    : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel?> create(modelClass: Class<T>) =
            TickerViewModel(repository) as T
}