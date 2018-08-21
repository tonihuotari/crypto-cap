package com.sombrero.cryptocap.features.coindetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.BaseActivity
import com.sombrero.cryptocap.common.FragmentNavigator

class DetailsActivity : BaseActivity() {
    var navigator: FragmentNavigator? = null

    override fun getFragmentContainerId(): Int {
        return R.id.detailsFrameLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        navigator = FragmentNavigator(this)

        val coinId = intent.getLongExtra(EXTRAS_COIN_ID, -1)
        navigator!!.openDetailsFragment(coinId)
    }

    companion object {
        val TAG = DetailsFragment::class.java.simpleName

        val EXTRAS_COIN_ID = "EXTRAS_COIN_ID"

        fun newIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRAS_COIN_ID, id)

            return intent
        }
    }
}