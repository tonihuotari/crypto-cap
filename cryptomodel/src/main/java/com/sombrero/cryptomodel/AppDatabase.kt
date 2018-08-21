package com.sombrero.cryptomodel

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sombrero.cryptomodel.coin.Coin
import com.sombrero.cryptomodel.coin.CoinDao
import com.sombrero.cryptomodel.starred.StarredCoin
import com.sombrero.cryptomodel.starred.StarredCoinDao
import com.sombrero.cryptomodel.ticker.TickerCoin
import com.sombrero.cryptomodel.ticker.TickerCoinDao

@Database(entities = [Coin::class, TickerCoin::class, StarredCoin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun tickerDao(): TickerCoinDao
    abstract fun starredDao(): StarredCoinDao

    companion object {
        const val DATABASE_NAME = "crypto-db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}