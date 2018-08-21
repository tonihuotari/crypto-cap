package com.sombrero.cryptocap.features.simple

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import com.sombrero.cryptocap.R

class CollapsingToolbarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collapsingtoolbar, container, false)
        val context = context ?: return view

        val scrollView = view.findViewById<ScrollView>(R.id.collapsingScrollView)
        val toolbarView = view.findViewById<Toolbar>(R.id.collapsingToolbarView)
        val titleView = view.findViewById<TextView>(R.id.collapsingTitleView)
        val topView = view.findViewById<View>(R.id.collapsingTopView)

        var darkColor = ResourcesCompat.getColor(resources, R.color.black, context.theme)
        val maxOffset = resources.getDimension(R.dimen.details_expanded_toolbar_height)

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            onCollapsingToolbarScrollChanged(scrollView.scrollY,
                    toolbarView, titleView, topView,
                    maxOffset,
                    darkColor)
        }

        return view
    }

    private fun onCollapsingToolbarScrollChanged(
            scrollY: Int,
            toolbar: Toolbar,
            titleView: TextView,
            topView: View,
            maxOffset: Float,
            darkColor: Int
    ) {
        val transparent = 0
        var color = transparent
        var translationY = 0F

        if (scrollY <= 0) {
            titleView.visibility = View.GONE
            color = transparent
        } else if (scrollY < maxOffset) {
            val ratio: Float = scrollY / maxOffset

            titleView.visibility = View.GONE
            color = ArgbEvaluator().evaluate(ratio, transparent, darkColor) as Int
            translationY = scrollY * 0.5f
        } else {
            titleView.visibility = View.VISIBLE
            color = darkColor
        }
        toolbar.setBackgroundColor(color)
        Log.e(TAG, "setTranslationY: $translationY")
        topView.translationY = translationY

    }

    companion object {
        private val TAG = CollapsingToolbarFragment.javaClass.simpleName

        fun newInstance() = CollapsingToolbarFragment()
    }

}