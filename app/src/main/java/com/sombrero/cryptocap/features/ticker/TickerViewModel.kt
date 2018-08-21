package com.sombrero.cryptocap.features.ticker

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.sombrero.cryptomodel.ticker.TickerCoin
import com.sombrero.cryptomodel.ticker.TickerCoinRepository

class TickerViewModel internal constructor(repository: TickerCoinRepository): ViewModel() {

    private val ticker = MediatorLiveData<List<TickerCoin>>()

    init {
        ticker.addSource(repository.getTicker(), ticker::setValue)

        repository.updateTicker()
    }

    fun getTicker() = ticker

}