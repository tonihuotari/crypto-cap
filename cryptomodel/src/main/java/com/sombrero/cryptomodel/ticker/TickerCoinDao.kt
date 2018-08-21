package com.sombrero.cryptomodel.ticker

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface TickerCoinDao {
    @Query("SELECT * FROM ticker ORDER BY rank")
    fun getCoins(): LiveData<List<TickerCoin>>

    @Query("SELECT * FROM ticker WHERE id = :coinId")
    fun getSpecific(coinId: Long): LiveData<TickerCoin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicker(coin: TickerCoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicker(coins: List<TickerCoin>)

    @Query("DELETE FROM ticker")
    fun removeTicker()


}