package com.sombrero.cryptocap.features.starred.list

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.sombrero.cryptomodel.starred.StarredCoin
import java.text.FieldPosition

class StarredAdapter : ListAdapter<StarredCoin, StarredViewHolder>(object : DiffUtil.ItemCallback<StarredCoin>() {
    override fun areItemsTheSame(oldCoin: StarredCoin, newCoin: StarredCoin): Boolean {
        return oldCoin.coinId == newCoin.coinId
    }

    override fun areContentsTheSame(oldCoin: StarredCoin, newCoin: StarredCoin): Boolean {
        return oldCoin == newCoin
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarredViewHolder {
        return StarredViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(viewHolder: StarredViewHolder, position: Int) {
        
    }


}