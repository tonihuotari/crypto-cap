package com.sombrero.cryptocap.features.starred.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.extensions.formattedName
import com.sombrero.cryptocap.extensions.symbolToIconId
import com.sombrero.cryptomodel.starred.StarredCoin

class StarredViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val starredImageView = itemView.findViewById<ImageView>(R.id.starredItemIconView)
    val starredItemTitleView = itemView.findViewById<TextView>(R.id.starredItemTitleView)

    fun bind(coin: StarredCoin) {
        starredImageView.setBackgroundResource(coin.coin?.symbolToIconId()
                ?: R.drawable.ic_coin_placeholder)
        starredItemTitleView.text = coin.coin?.formattedName() ?: "LOADING"
    }

    companion object {
        fun newInstance(parent: ViewGroup): StarredViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_starred, parent, false)
            return StarredViewHolder(view)
        }
    }

}