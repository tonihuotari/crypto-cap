package com.sombrero.cryptocap.features.coindetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sombrero.cryptomodel.starred.StarredCoinRepository
import com.sombrero.cryptomodel.ticker.TickerCoinRepository

class DetailsViewModelFactory(
        private val coinId: Long,
        private val tickerCoinRepository: TickerCoinRepository,
        private val starredCoinRepository: StarredCoinRepository)
    : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            DetailsViewModel(coinId, tickerCoinRepository, starredCoinRepository) as T
}