package com.sombrero.cryptocap.features.coinlisting

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sombrero.cryptomodel.coin.CoinRepository

class CoinListingViewModelFactory(private val repository: CoinRepository)
    : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            CoinListingViewModel(repository) as T
}