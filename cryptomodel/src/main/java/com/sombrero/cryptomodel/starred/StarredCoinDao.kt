package com.sombrero.cryptomodel.starred

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface StarredCoinDao {
    @Query("SELECT * FROM starred")
    fun getStarredCoins(): LiveData<List<StarredCoin>>

    @Query("SELECT * FROM starred WHERE id = :coinId")
    fun getStarredCoin(coinId: Long): LiveData<StarredCoin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStarredCoin(coin: StarredCoin)

    @Delete
    fun removeStarredCoin(coin: StarredCoin)
}