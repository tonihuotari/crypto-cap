package com.sombrero.cryptocap.features.coinlisting

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.ActivityNavigator
import com.sombrero.cryptocap.common.InjectorUtils
import com.sombrero.cryptocap.features.coinlisting.list.CoinListingPagedAdapter
import com.sombrero.cryptomodel.coin.Coin

class CoinListingFragment : Fragment() {

    lateinit var viewModel: CoinListingViewModel

    val adapter = CoinListingPagedAdapter()

    private val listObserver: Observer<PagedList<Coin>> by lazy {
        Observer<PagedList<Coin>> { list ->
            Log.d(TAG, "listObserver: submitlist")
            adapter.submitList(list)
        }
    }

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
        val searchBackgroundView = view.findViewById<FrameLayout>(R.id.coinListingSearchBackgroundView)
        val recyclerView = view.findViewById<RecyclerView>(R.id.coinListingRecyclerView)

        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                // Do nothing
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                subscribeUi(text)
                return true
            }

        })

        view as ConstraintLayout
        searchView.setOnQueryTextFocusChangeListener { _, focused ->
            when (focused) {
                true -> animateSearchView(view, searchBackgroundView, searchView)
                false -> reverseAnimateSearchView(view, searchBackgroundView, searchView)
            }
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        subscribeUi()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unsubscribeUi()
    }

    private fun unsubscribeUi() {
        viewModel.getCoins().removeObserver(listObserver)
    }

    private fun subscribeUi(filter: String? = null) {
        unsubscribeUi()
        viewModel.onSearchText(filter)

        viewModel.getCoins().observe(viewLifecycleOwner, listObserver)
    }

    private fun animateSearchView(constraintLayout: ConstraintLayout, backgroundView: FrameLayout, searchView: SearchView) {

        val constraint = ConstraintSet()
        constraint.clone(constraintLayout)
        constraint.connect(backgroundView.id, ConstraintSet.START, searchView.id, ConstraintSet.START, 0)
        constraint.connect(backgroundView.id, ConstraintSet.END, searchView.id, ConstraintSet.END, 0)
        constraint.connect(backgroundView.id, ConstraintSet.BOTTOM, searchView.id, ConstraintSet.BOTTOM, 0)
        constraint.connect(backgroundView.id, ConstraintSet.TOP, searchView.id, ConstraintSet.TOP, 0)

        TransitionManager.beginDelayedTransition(backgroundView)
        constraint.applyTo(constraintLayout)
    }

    private fun reverseAnimateSearchView(constraintLayout: ConstraintLayout, backgroundView: FrameLayout, searchView: SearchView) {

        val margin = resources.getDimension(R.dimen.search_box_margin)

        val constraint = ConstraintSet()
        constraint.clone(constraintLayout)
        constraint.connect(backgroundView.id, ConstraintSet.START, searchView.id, ConstraintSet.START, margin.toInt())
        constraint.connect(backgroundView.id, ConstraintSet.END, searchView.id, ConstraintSet.END, margin.toInt())
        constraint.connect(backgroundView.id, ConstraintSet.BOTTOM, searchView.id, ConstraintSet.BOTTOM, margin.toInt())
        constraint.connect(backgroundView.id, ConstraintSet.TOP, searchView.id, ConstraintSet.TOP, margin.toInt())

        TransitionManager.beginDelayedTransition(backgroundView)
        constraint.applyTo(constraintLayout)
    }

    companion object {
        val TAG = CoinListingFragment::class.java.simpleName

        fun newInstance(): CoinListingFragment {
            return CoinListingFragment()
        }
    }

}