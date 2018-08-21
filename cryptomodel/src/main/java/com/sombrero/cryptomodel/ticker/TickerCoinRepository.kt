package com.sombrero.cryptomodel.ticker

import android.util.Log
import com.sombrero.cryptoclient.CryptoClient
import com.sombrero.cryptoclient.apimodels.ApiCoinDetails
import io.reactivex.schedulers.Schedulers

class TickerCoinRepository private constructor(
        private val tickerDao: TickerCoinDao,
        private val client: CryptoClient) {


    fun getTicker(coinId: Long) = tickerDao.getSpecific(coinId)

    fun getTicker() = tickerDao.getCoins()

    fun updateTicker() {
        client.getTicker10()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            val list = result.data.map { convert(it.value) }

                            insertTicker(list)
                        }, { error -> Log.e(TAG, "Failed to update ticker", error) }
                )
    }

    fun updateTicker(coinId: Long) {
        client.getTickerSpecific(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            val ticker = convert(result.data)

                            insertTicker(ticker)
                        }, { error -> Log.e(TAG, "Failed to update specific ticker", error) }
                )
    }

    private fun convert(apiCoin: ApiCoinDetails): TickerCoin {
        return TickerCoin(
                apiCoin.id,
                apiCoin.name,
                apiCoin.symbol,
                apiCoin.rank,
                apiCoin.quotes.USD.percent_change_24h,
                apiCoin.quotes.USD.price,
                apiCoin.quotes.USD.market_cap,
                apiCoin.quotes.USD.volume_24h,
                apiCoin.circulating_supply,
                apiCoin.max_supply
        )
    }

    private fun insertTicker(coins: List<TickerCoin>) {
        tickerDao.removeTicker()
        tickerDao.insertTicker(coins)
    }

    private fun insertTicker(coin: TickerCoin) {
        tickerDao.insertTicker(coin)
    }

    companion object {
        val TAG = TickerCoinRepository::class.java.simpleName

        @Volatile
        private var repository: TickerCoinRepository? = null

        fun getInstance(tickerDao: TickerCoinDao, client: CryptoClient): TickerCoinRepository {
            return repository ?: synchronized(this) {
                repository ?: TickerCoinRepository(tickerDao, client)
                        .also { repository = it }
            }
        }
    }


}