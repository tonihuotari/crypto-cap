package com.sombrero.cryptocap.features.starred

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.common.ActivityNavigator
import com.sombrero.cryptocap.common.InjectorUtils
import com.sombrero.cryptocap.features.starred.list.StarredAdapter

class StarredFragment : Fragment() {

    lateinit var viewModel: StarredViewModel

    val adapter = StarredAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        adapter.navigator = ActivityNavigator(context!!)
    }

    override fun onDetach() {
        super.onDetach()
        adapter.navigator = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_starred, container, false)
        val context = context ?: return view

        val factory = InjectorUtils.provideStarredViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(StarredViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.starredRecyclerView)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        subscribeUi(adapter)

        return view
    }

    private fun subscribeUi(adapter: StarredAdapter) {
        viewModel.getStarredCoins().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    companion object {
        fun newInstance() = StarredFragment()
    }
}