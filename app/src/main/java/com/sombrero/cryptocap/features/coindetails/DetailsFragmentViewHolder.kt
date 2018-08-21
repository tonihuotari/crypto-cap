package com.sombrero.cryptocap.features.coindetails

import android.animation.ArgbEvaluator
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.sombrero.cryptocap.R
import com.sombrero.cryptocap.extensions.formattedName
import com.sombrero.cryptocap.extensions.symbolToToolbarId
import com.sombrero.cryptomodel.starred.StarredCoin
import com.sombrero.cryptomodel.ticker.TickerCoin

class DetailsFragmentViewHolder(val view: View) {
    private val scrollView = view.findViewById<ScrollView>(R.id.detailsScrollView)
    private val toolbar = view.findViewById<Toolbar>(R.id.detailsToolbar)
    private val toolbarTitleView = view.findViewById<TextView>(R.id.detailsToolbarTitleView)
    private val collapsingImageView = view.findViewById<ImageView>(R.id.detailsCollapsingImageView)

    val bottomContainer = view.findViewById<FrameLayout>(R.id.detailsContainerView)
    val detailsFAB = view.findViewById<FloatingActionButton>(R.id.detailsFAB)


    init {
        initCollapsingToolbar(view.context, scrollView,
                toolbar, toolbarTitleView, collapsingImageView)
    }

    fun bindView(starredCoin: StarredCoin?) {
        detailsFAB.isSelected = starredCoin != null
    }

    fun bindView(coin: TickerCoin) {
        collapsingImageView.setBackgroundResource(coin.symbolToToolbarId())
        toolbarTitleView.text = coin.formattedName()
    }

    private fun initCollapsingToolbar(context: Context,
                                      scrollView: ScrollView,
                                      toolbar: Toolbar,
                                      titleView: TextView,
                                      collapsingImage: ImageView) {
        var darkColor = ResourcesCompat.getColor(context.resources, R.color.colorPrimary, context.theme)
        val expandedImageHeight = context.resources.getDimension(R.dimen.details_expanded_toolbar_height)

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            onCollapsingToolbarScrollChanged(scrollView.scrollY,
                    toolbar, titleView, collapsingImage,
                    expandedImageHeight,
                    darkColor)
        }
    }

    companion object {
        fun newInstance(inflater: LayoutInflater, container: ViewGroup?): DetailsFragmentViewHolder {
            val view = inflater.inflate(R.layout.fragment_details, container, false)
            return DetailsFragmentViewHolder(view)
        }

        private fun onCollapsingToolbarScrollChanged(
                scrollY: Int,
                toolbar: Toolbar,
                toolbartitleView: TextView,
                expandedImageView: View,
                expandedImageHeight: Float,
                darkColor: Int
        ) {
            val transparent = 0
            var color = transparent
            var translationY = 0F

            if (scrollY <= 0) {
                toolbartitleView.visibility = View.GONE
                color = transparent
            } else if (scrollY < expandedImageHeight) {
                val ratio: Float = scrollY / expandedImageHeight

                toolbartitleView.visibility = View.GONE
                color = ArgbEvaluator().evaluate(ratio, transparent, darkColor) as Int
                translationY = scrollY * 0.5f
            } else {
                toolbartitleView.visibility = View.VISIBLE
                color = darkColor
            }
            toolbar.setBackgroundColor(color)
            expandedImageView.translationY = translationY
        }
    }
}