package com.sombrero.cryptocap.features.coindetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.common.InjectorUtils

class DetailsFragment : Fragment() {

    lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentViewHolder = DetailsFragmentViewHolder.newInstance(inflater, container)
        val view = fragmentViewHolder.view

        val context = context ?: return view
        val id = arguments?.getLong(ARG_COIN_ID) ?: return view

        val factory = InjectorUtils.provideDetailsViewModelFactory(context, id)
        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)

        val detailsViewHolder = CoinDetailsViewHolder.newInstance(fragmentViewHolder.bottomContainer)
        subscribeUi(detailsViewHolder, fragmentViewHolder)

        fragmentViewHolder.favoriteButton.setOnClickListener {

            if (fragmentViewHolder.favoriteButton.isSelected) {
                viewModel.unstarCoin()
            } else {
                viewModel.starCoin()
            }
        }

        return view
    }

    private fun subscribeUi(detailsViewHolder: CoinDetailsViewHolder, fragmentViewHolder: DetailsFragmentViewHolder) {
        viewModel.getCoinDetails().observe(viewLifecycleOwner, Observer { coinDetails ->
            coinDetails?.let {
                detailsViewHolder.bindView(it)
                fragmentViewHolder.bindView(it)
            }
        })

        viewModel.getStarredCoin().observe(viewLifecycleOwner, Observer {
            fragmentViewHolder.bindView(it)
        })
    }


    companion object {
        val TAG = DetailsFragment::class.java.simpleName

        val ARG_COIN_ID = "ARG_COIN_ID"

        fun newInstance(coinId: Long): DetailsFragment {
            val bundle = Bundle().apply { putLong(ARG_COIN_ID, coinId) }
            return DetailsFragment().apply { arguments = bundle }
        }
    }

}