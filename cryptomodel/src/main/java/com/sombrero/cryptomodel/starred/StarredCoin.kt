package com.sombrero.cryptomodel.starred

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sombrero.cryptomodel.ticker.TickerCoin

@Entity(tableName = "starred")
data class StarredCoin(@PrimaryKey @ColumnInfo(name = "coin_id") val coinId: Long) {
    @Embedded
    var coin: TickerCoin? = null
}