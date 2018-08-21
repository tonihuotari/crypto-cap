package com.sombrero.cryptocap.features.coinlisting

import android.arch.lifecycle.ViewModel
import com.sombrero.cryptomodel.coin.CoinRepository

class CoinListingViewModel internal constructor(repository: CoinRepository) : ViewModel() {

    private val coinList = repository.getCoins().build()

    init {
        repository.updateCoins()
    }

    fun getCoins() = coinList
}