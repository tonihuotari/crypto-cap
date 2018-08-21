package com.sombrero.cryptocap.common

import android.content.Context
import com.sombrero.cryptocap.features.coindetails.DetailsActivity

class ActivityNavigator(val context: Context) {

    fun openDetailsActivity(coinId: Long) {
        context.startActivity(
                DetailsActivity.newIntent(context, coinId)
        )
    }

}