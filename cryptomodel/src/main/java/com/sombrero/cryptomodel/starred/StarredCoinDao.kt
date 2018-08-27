package com.sombrero.cryptomodel.starred

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface StarredCoinDao {
    @Query("SELECT starred.*, ticker.* FROM starred LEFT JOIN ticker ON starred.coin_id = ticker.id")
    fun getStarredCoins(): LiveData<List<StarredCoin>>

    @Query("SELECT starred.*, ticker.* FROM starred LEFT JOIN ticker ON starred.coin_id = ticker.id WHERE starred.coin_id = :coinId")
    fun getStarredCoin(coinId: Long): LiveData<StarredCoin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStarredCoin(coin: StarredCoin)

    @Delete
    fun removeStarredCoin(coin: StarredCoin)
}