package com.sombrero.cryptocap.features.coindetails

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.CoinUtils
import com.sombrero.cryptocap.extensions.symbolToIconId
import com.sombrero.cryptomodel.ticker.TickerCoin

class CoinDetailsViewHolder(val view: View) {

    val iconView: ImageView = view.findViewById(R.id.detailsIconView)
    val titleView: TextView = view.findViewById(R.id.detailsTitleView)
    val priceView: TextView = view.findViewById(R.id.detailsPriceView)
    val marketCapView: TextView = view.findViewById(R.id.detailsMarketCapView)
    val volumeView: TextView = view.findViewById(R.id.detailsVolumeView)
    val circulatingSupplyView: TextView = view.findViewById(R.id.detailsCirculatingSupplyView)
    val supplyView: TextView = view.findViewById(R.id.detailsMaxSupplyView)

    fun bindView(coin: TickerCoin) {

        iconView.setBackgroundResource(coin.symbolToIconId())
        titleView.text = "${coin.name} (${coin.symbol})"
        priceView.text = formatPriceText(view.context, coin)
        marketCapView.text = CoinUtils.formatAmount(coin.marketCapUSD, prefix = "$", postfix = "USD")
        volumeView.text = CoinUtils.formatAmount(coin.volumeUSD, prefix = "$", postfix = "USD")
        circulatingSupplyView.text = CoinUtils.formatAmount(coin.circulatingSupply, postfix = coin.symbol)
        supplyView.text = CoinUtils.formatAmount(coin.maxSupply, postfix = coin.symbol)

        String.format("%.0f %s", coin.maxSupply, coin.symbol)

    }

    private fun formatPriceText(context: Context, coin: TickerCoin): SpannableString {

        var text = CoinUtils.formatAmount(coin.usdPrice, prefix = "$")
        val startOfPriceIndex = 0
        val endOfPriceIndex = text.length

        val startOfCurrencyIndex = text.length
        text += " USD "
        val endOfCurrencyIndex = text.length

        val startOfColorIndex = text.length
        text += "(${coin.percentChange24h}%)"
        val endOfColorIndex = text.length

        val spannableString = SpannableString(text)

        val priceColor = ResourcesCompat.getColor(
                context.resources,
                R.color.black,
                context.theme)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), startOfPriceIndex, endOfPriceIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(priceColor), startOfPriceIndex, endOfPriceIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(RelativeSizeSpan(1.5f), startOfPriceIndex, endOfPriceIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(RelativeSizeSpan(1.0f), startOfCurrencyIndex, endOfCurrencyIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val changeColor = CoinUtils.percentChangeColor(context, coin.percentChange24h)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), startOfColorIndex, endOfColorIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(changeColor), startOfColorIndex, endOfColorIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(RelativeSizeSpan(1.5f), startOfColorIndex, endOfColorIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannableString
    }

    companion object {
        fun newInstance(parent: ViewGroup, attachToRoot: Boolean = true): CoinDetailsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_coin_details, parent, attachToRoot)
            return CoinDetailsViewHolder(view)
        }
    }

}