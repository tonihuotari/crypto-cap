package com.sombrero.cryptomodel.coin

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CoinDao {

    @Query("SELECT * FROM coins ORDER BY name")
    fun getCoins(): DataSource.Factory<Int, Coin>

    @Query("SELECT * FROM coins WHERE name LIKE :like ORDER BY name")
    fun getCoins(like: String): DataSource.Factory<Int, Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoin(coin: Coin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoins(coins: List<Coin>)

}