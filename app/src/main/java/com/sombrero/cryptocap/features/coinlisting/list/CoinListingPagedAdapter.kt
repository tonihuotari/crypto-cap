package com.sombrero.cryptocap.features.coinlisting.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.sombrero.cryptocap.common.ActivityNavigator
import com.sombrero.cryptomodel.coin.Coin

class CoinListingPagedAdapter : PagedListAdapter<Coin, CoinListingViewHolder>(
        object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldCoin: Coin, newCoin: Coin): Boolean = oldCoin.id == newCoin.id

            override fun areContentsTheSame(oldCoin: Coin, newCoin: Coin): Boolean = oldCoin == newCoin
        }
) {
    var navigator: ActivityNavigator? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListingViewHolder {
        return CoinListingViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(viewHolder: CoinListingViewHolder, position: Int) {

        val coin = getItem(position)

        viewHolder.onBind(coin)
        viewHolder.itemView.setOnClickListener {
            coin?.let {
                navigator?.openDetailsActivity(coin.id)
            }
        }
    }

}