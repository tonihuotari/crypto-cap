package com.sombrero.cryptocap.common

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import com.sombrero.cryptocap.R
import com.sombrero.cryptomodel.coin.Coin
import com.sombrero.cryptomodel.ticker.TickerCoin
import java.text.NumberFormat
import java.util.*

object CoinUtils {
    fun formatAmount(amount: Double, prefix: String? = null, postfix: String? = null): String {
        val format = NumberFormat.getCurrencyInstance(Locale.US)

        return format
                .format(amount)
                .replace(format.currency.getSymbol(Locale.US), "")
                .also {
                    var result = it
                    prefix?.let { result = "$prefix$result" }
                    postfix?.let { result = "$result $postfix" }
                    return result
                }
    }

    fun percentChangeColor(context: Context, percentChange: Double): Int {
        val changeTextColorId = if (percentChange > 0) {
            R.color.green
        } else if (percentChange < 0) {
            R.color.red
        } else {
            R.color.black
        }

        return ResourcesCompat.getColor(
                context.resources,
                changeTextColorId,
                context.theme)
    }

}