package com.sombrero.cryptomodel.ticker

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "ticker")
data class TickerCoin(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        val name: String,
        val symbol: String,
        val rank: Long,
        val percentChange24h: Double,
        val usdPrice: Double,
        val marketCapUSD: Double,
        val volumeUSD: Double,
        val circulatingSupply: Double,
        val maxSupply: Double)