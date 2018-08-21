package com.sombrero.cryptocap.features.ticker.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sombrero.cryptocap.common.ActivityNavigator
import com.sombrero.cryptomodel.ticker.TickerCoin

class TickerAdapter : RecyclerView.Adapter<TickerViewHolder>() {
    private val HEADER_TYPE = 0
    private val ITEM_TYPE = 1

    var navigator: ActivityNavigator? = null
    var coins: List<TickerCoin>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        return when (viewType) {
            HEADER_TYPE -> TickerHeaderViewHolder.newInstance(parent)
            ITEM_TYPE -> TickerViewHolder.newInstance(parent)
            else -> throw IllegalStateException("ViewHolder for type $viewType not implemented")
        }
    }

    override fun getItemCount(): Int {
        return 1 + (coins?.size ?: 0)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_TYPE else ITEM_TYPE
    }

    override fun onBindViewHolder(viewHolder: TickerViewHolder, position: Int) {

        when (getItemViewType(position)) {
            HEADER_TYPE -> {
            } // do nothing
            ITEM_TYPE -> {
                val coin = coins!![position - 1]
                viewHolder.onBind(coin)
                viewHolder.itemView.setOnClickListener { navigator?.openDetailsActivity(coin.id) }
            }
        }
    }
}