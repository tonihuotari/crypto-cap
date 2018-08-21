package com.sombrero.cryptocap.features.coinlisting

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.ActivityNavigator
import com.sombrero.cryptocap.common.InjectorUtils
import com.sombrero.cryptocap.features.coinlisting.list.CoinListingPagedAdapter

class CoinListingFragment : Fragment() {

    lateinit var viewModel: CoinListingViewModel

    val adapter = CoinListingPagedAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        adapter.navigator = ActivityNavigator(context!!)
    }

    override fun onDetach() {
        super.onDetach()
        adapter.navigator = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coinlisting, container, false)
        val context = context ?: return view

        val factory = InjectorUtils.provideCoinListingViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(CoinListingViewModel::class.java)

        val searchView = view.findViewById<SearchView>(R.id.coinListingSearchView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.coinListingRecyclerView)

        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                // Do nothing
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                viewModel.onSearchText(text)
                subscribeUi(adapter)
                return true
            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        subscribeUi(adapter)

        return view
    }

    private fun subscribeUi(adapter: CoinListingPagedAdapter) {
        Log.e(TAG, "subscribeUi")
        viewModel.getCoins().observe(viewLifecycleOwner, Observer { list ->
            Log.e(TAG, "subscribeUi: submitlist")
            adapter.submitList(list)
        })
    }

    companion object {
        val TAG = CoinListingFragment::class.java.simpleName

        fun newInstance(): CoinListingFragment {
            return CoinListingFragment()
        }
    }

}