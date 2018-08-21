package com.sombrero.cryptocap.features.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.BaseActivity
import com.sombrero.cryptocap.common.FragmentNavigator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    var navigator: FragmentNavigator? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mainNavigationView.postDelayed({
                    navigator?.openTickerFragment()
                }, 100)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_coinlisting -> {
                mainNavigationView.postDelayed({
                    navigator?.openCoinListingFragment()
                }, 100)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mainNavigationView.postDelayed({
                    navigator?.openStarredFragment()
                }, 100)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun getFragmentContainerId(): Int {
        return R.id.mainFrameLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator = FragmentNavigator(this)

        val mainNavigationView = findViewById<BottomNavigationView>(R.id.mainNavigationView)

        mainNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
