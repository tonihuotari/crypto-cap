package com.sombrero.cryptomodel.starred

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "starred")
data class StarredCoin(@PrimaryKey @ColumnInfo(name = "id") val coinId: Long) {
//    val coin: TickerCoin? = null
}