package com.sombrero.cryptocap.features.ticker

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.ActivityNavigator
import com.sombrero.cryptocap.common.BaseActivity
import com.sombrero.cryptocap.common.FragmentNavigator
import com.sombrero.cryptocap.common.InjectorUtils
import com.sombrero.cryptocap.features.ticker.list.TickerAdapter
import com.sombrero.cryptoclient.CryptoClient

class TickerFragment : Fragment() {

    lateinit var viewModel: TickerViewModel

    val adapter = TickerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        adapter.navigator = ActivityNavigator(context!!)
    }

    override fun onDetach() {
        super.onDetach()
        adapter.navigator = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ticker, container, false)
        val context = context ?: return view

        val factory = InjectorUtils.provideTickerViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(TickerViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.tickerRecyclerView)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        subscribeUi(adapter)

        return view
    }

    private fun subscribeUi(adapter: TickerAdapter) {
        viewModel.getTicker().observe(viewLifecycleOwner, Observer { coins ->
            adapter.coins = coins
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        val TAG = TickerFragment::class.java.simpleName

        fun newInstance(): TickerFragment {
            return TickerFragment()
        }
    }
}