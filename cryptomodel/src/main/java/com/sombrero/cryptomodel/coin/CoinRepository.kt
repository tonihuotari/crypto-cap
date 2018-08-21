package com.sombrero.cryptomodel.coin

import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.sombrero.cryptoclient.CryptoClient
import com.sombrero.cryptoclient.apimodels.ApiCoin
import io.reactivex.schedulers.Schedulers

class CoinRepository private constructor(
        private val coinDao: CoinDao,
        private val client: CryptoClient) {

    fun getCoins(): LivePagedListBuilder<Int, Coin> {
        return LivePagedListBuilder(coinDao.getCoins(), 30)
    }

    fun getCoins(filter: String): LivePagedListBuilder<Int, Coin> {
        // Adding '%' to end of filter string in order to match names that start with the string
        return LivePagedListBuilder(coinDao.getCoins("$filter%"), 30)
    }

    fun updateCoins() {
        client.getCoinListing()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            Log.d(TAG, "Successfully updated coins")
                            insertCoins(convert(result.data))
                        }, { error -> Log.e(TAG, "Failed to update coins", error) }

                )

    }

    private fun convert(apiCoins: List<ApiCoin>): List<Coin> {
        return apiCoins.map { Coin(it.id, it.name, it.symbol, it.website_slug) }
    }

    private fun insertCoins(coins: List<Coin>) {
        coinDao.insertCoins(coins)
    }

    companion object {
        val TAG = CoinRepository::class.java.simpleName

        @Volatile
        private var instance: CoinRepository? = null

        fun getInstance(coinDao: CoinDao, client: CryptoClient) =
                instance ?: synchronized(this) {
                    instance ?: CoinRepository(coinDao, client).also { instance = it }
                }
    }
}