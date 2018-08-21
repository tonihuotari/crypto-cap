package com.sombrero.cryptocap.features.coinlisting

import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.sombrero.cryptomodel.coin.CoinRepository

class CoinListingViewModel internal constructor(val repository: CoinRepository) : ViewModel() {

    private var coinList = repository.getCoins().build()

    init {
        repository.updateCoins()
    }

    fun getCoins() = coinList

    fun onSearchText(text: String?) {
        coinList = when (TextUtils.isEmpty(text)) {
            true -> repository.getCoins()
            else -> repository.getCoins(text!!)
        }.build()
    }
}