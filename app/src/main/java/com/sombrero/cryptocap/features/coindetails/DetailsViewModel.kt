package com.sombrero.cryptocap.features.coindetails

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.sombrero.cryptomodel.starred.StarredCoin
import com.sombrero.cryptomodel.starred.StarredCoinRepository
import com.sombrero.cryptomodel.ticker.TickerCoin
import com.sombrero.cryptomodel.ticker.TickerCoinRepository


class DetailsViewModel internal constructor(
        private val coinId: Long,
        tickerCoinRepository: TickerCoinRepository,
        private val starredCoinRepository: StarredCoinRepository) : ViewModel() {

    private val coinDetails = MediatorLiveData<TickerCoin>()
    private val starredCoin = MediatorLiveData<StarredCoin>()

    init {
        coinDetails.addSource(tickerCoinRepository.getTicker(coinId), coinDetails::setValue)
        starredCoin.addSource(starredCoinRepository.getStarredCoin(coinId), starredCoin::setValue)

        tickerCoinRepository.updateTicker(coinId)
    }

    fun getCoinDetails() = coinDetails

    fun getStarredCoin() = starredCoin

    fun starCoin() {
        starredCoinRepository.starCoin(coinId)
    }

    fun unstarCoin() {
        starredCoinRepository.unstarCoin(coinId)
    }
}