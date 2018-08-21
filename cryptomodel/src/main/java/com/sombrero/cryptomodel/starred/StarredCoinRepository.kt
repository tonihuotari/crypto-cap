package com.sombrero.cryptomodel.starred

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class StarredCoinRepository private constructor(
        private val starredCoinDao: StarredCoinDao) {

    fun getStarredCoins() = starredCoinDao.getStarredCoins()

    fun getStarredCoin(coinId: Long) = starredCoinDao.getStarredCoin(coinId)

    fun starCoin(coinId: Long) {
        val coin = StarredCoin(coinId)

        Observable.just(coin)
                .subscribeOn(Schedulers.io())
                .map { starredCoinDao.insertStarredCoin(coin) }
                .subscribe()
    }

    fun unstarCoin(coinId: Long) {
        val coin = StarredCoin(coinId)

        Observable.just(coin)
                .subscribeOn(Schedulers.io())
                .map { starredCoinDao.removeStarredCoin(coin) }
                .subscribe()
    }


    companion object {
        @Volatile
        private var repository: StarredCoinRepository? = null

        fun getInstance(starredCoinDao: StarredCoinDao): StarredCoinRepository {
            return repository ?: synchronized(this) {
                repository ?: StarredCoinRepository(starredCoinDao)
                        .also { repository = it }
            }
        }
    }

}