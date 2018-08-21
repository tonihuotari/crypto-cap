package com.sombrero.cryptoclient.apimodels

class ApiCoinDetails(
        val id: Long,
        val name: String,
        val symbol: String,
        val website_slug: String,
        val rank: Long,
        val circulating_supply: Double,
        val total_supply: Double,
        val max_supply: Double,
        val quotes: Quotes,
        val last_updated: String) {


    class Quotes(val USD: QuoteCurrency)

    class QuoteCurrency(
            val price: Double,
            val volume_24h: Double,
            val market_cap: Double,
            val percent_change_1h: Double,
            val percent_change_24h: Double,
            val percent_change_7d: Double
    )
}