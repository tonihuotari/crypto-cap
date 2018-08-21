package com.sombrero.cryptocap.features.ticker.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.R

class TickerHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup): TickerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticker_header, parent, false)
            return TickerViewHolder(view)
        }
    }
}