package com.sombrero.cryptocap.extensions

import com.sombrero.cryptocap.R
import com.sombrero.cryptomodel.coin.Coin
import com.sombrero.cryptomodel.ticker.TickerCoin

fun Coin.symbolToIconId(): Int {
    return coinSymbolToIconId(symbol)
}

fun TickerCoin.symbolToIconId(): Int {
    return coinSymbolToIconId(symbol)
}

fun TickerCoin.symbolToToolbarId(): Int {
    return coinSymbolToToolbarId(symbol)
}

fun TickerCoin.formattedName(): String {
    return "$name ($symbol)"
}

private fun coinSymbolToIconId(symbol: String?): Int {
    return when (symbol) {
        "BTC" -> R.drawable.ic_coin_btc
        "EOS" -> R.drawable.ic_coin_eos
        "ETH" -> R.drawable.ic_coin_eth
        "LTC" -> R.drawable.ic_coin_ltc
        "MIOTA" -> R.drawable.ic_coin_miota
        "TRX" -> R.drawable.ic_coin_trx
        "USDT" -> R.drawable.ic_coin_usdt
        "XLM" -> R.drawable.ic_coin_xlm
        "XMR" -> R.drawable.ic_coin_xmr
        "XRP" -> R.drawable.ic_coin_xpr

        else -> R.drawable.ic_monetization_on_black_24dp
    }
}

private fun coinSymbolToToolbarId(symbol: String?): Int {
    return when (symbol) {
        "BTC" -> R.drawable.toolbar_btc
        "ETH" -> R.drawable.toolbar_eth
        else -> R.drawable.toolbar_placeholder
    }
}