package com.sombrero.cryptocap.features.starred.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.R

class StarredViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup): StarredViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_starred, parent, false)
            return StarredViewHolder(view)
        }
    }

}