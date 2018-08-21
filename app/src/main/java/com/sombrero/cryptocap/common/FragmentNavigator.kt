package com.sombrero.cryptocap.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.sombrero.cryptocap.features.coindetails.DetailsFragment
import com.sombrero.cryptocap.features.coinlisting.CoinListingFragment
import com.sombrero.cryptocap.features.simple.CollapsingToolbarFragment
import com.sombrero.cryptocap.features.simple.ColorFragment
import com.sombrero.cryptocap.features.starred.StarredFragment
import com.sombrero.cryptocap.features.ticker.TickerFragment

class FragmentNavigator(val fragmentManager: FragmentManager, val containerLayoutId: Int) {

    constructor(activity: BaseActivity) : this(activity.supportFragmentManager, activity.getFragmentContainerId())

    fun openColorFragment(color: Int) {
        openFragment(ColorFragment.newInstance(color))
    }

    fun openCollapsingToolbarFragment() {
        openFragment(CollapsingToolbarFragment.newInstance())
    }

    fun openCoinListingFragment() {
        openFragment(CoinListingFragment.newInstance())
    }

    fun openTickerFragment() {
        openFragment(TickerFragment.newInstance())
    }

    fun openDetailsFragment(coinId: Long) {
        openFragment(DetailsFragment.newInstance(coinId))
    }

    fun openStarredFragment() {
        openFragment(StarredFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment, addToBackStack: String? = null) {
        val fragmentTransaction = fragmentManager
                .beginTransaction()
                .replace(containerLayoutId, fragment)

        addToBackStack?.let { fragmentTransaction.addToBackStack(it) }

        fragmentTransaction.commit()
    }

}