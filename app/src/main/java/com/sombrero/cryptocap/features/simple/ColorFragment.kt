package com.sombrero.cryptocap.features.simple

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.R

class ColorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_color, container, false)

        val color = ResourcesCompat.getColor(
                resources, arguments!!.getInt(ARG_COLOR_ID), context!!.theme)

        view.setBackgroundColor(color)

        return view
    }

    companion object {

        private val ARG_COLOR_ID = "ARG_COLOR_ID"

        fun newInstance(colorId: Int): ColorFragment {

            val bundle = Bundle()
            bundle.putInt(ARG_COLOR_ID, colorId)

            val fragment = ColorFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

}