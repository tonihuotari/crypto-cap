package com.sombrero.cryptocap.features.ticker.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.CoinUtils
import com.sombrero.cryptocap.extensions.symbolToIconId
import com.sombrero.cryptomodel.ticker.TickerCoin

class TickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView = itemView.findViewById<TextView>(R.id.tickerItemTitleView)
    val nameView = itemView.findViewById<TextView>(R.id.tickerItemNameView)
    val changeView = itemView.findViewById<TextView>(R.id.ticker24hChangeView)
    val priceView = itemView.findViewById<TextView>(R.id.tickerPriceView)
    val iconView = itemView.findViewById<ImageView>(R.id.tickerItemIconView)

    fun onBind(coin: TickerCoin) {
        titleView.text = coin.symbol
        nameView.text = coin.name
        iconView.setBackgroundResource(coin.symbolToIconId())

        format24ChangeTextView(coin.percentChange24h)
        formatUSDPriceTextView(coin.usdPrice)
    }

    private fun format24ChangeTextView(percentChange24h: Double) {
        val changeTextColor = CoinUtils.percentChangeColor(itemView.context, percentChange24h)

        changeView.setTextColor(changeTextColor)
        changeView.text = percentChange24h.toString()
    }

    private fun formatUSDPriceTextView(price: Double) {
        priceView.text = String.format("$ %.2f", price)
    }

    companion object {
        fun newInstance(parent: ViewGroup): TickerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticker, parent, false)
            return TickerViewHolder(view)
        }
    }

}