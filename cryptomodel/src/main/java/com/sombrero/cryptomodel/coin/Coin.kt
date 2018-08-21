package com.sombrero.cryptomodel.coin

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "coins")
data class Coin(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        val name: String,
        val symbol: String?,
        val websiteSlug: String?
)