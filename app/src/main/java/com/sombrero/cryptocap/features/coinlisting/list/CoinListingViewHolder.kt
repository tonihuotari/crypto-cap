package com.sombrero.cryptocap.features.coinlisting.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.extensions.formattedName
import com.sombrero.cryptocap.extensions.symbolToIconId
import com.sombrero.cryptomodel.coin.Coin

class CoinListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView = itemView.findViewById<TextView>(R.id.coinListingItemTitleView)
    val iconView = itemView.findViewById<ImageView>(R.id.coinListingItemIconView)

    fun onBind(coin: Coin?) {

        when (coin) {
            is Coin -> {
                titleView.text = coin.formattedName()
                iconView.setBackgroundResource(coin.symbolToIconId())
            }
            else -> {
                titleView.text = "LOADING"
                iconView.setBackgroundResource(R.drawable.ic_coin_placeholder)
            }
        }

    }

    companion object {
        fun newInstance(parent: ViewGroup): CoinListingViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coinlisting, parent, false)
            return CoinListingViewHolder(view)
        }
    }

}