package com.sombrero.cryptocap.features.coindetails

import android.animation.ArgbEvaluator
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
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
    private val detailsProgressBarContainer = view.findViewById<View>(R.id.detailsProgressBarContainer)

    val bottomContainer = view.findViewById<FrameLayout>(R.id.detailsContainerView)
    val favoriteButton = view.findViewById<LottieAnimationView>(R.id.detailsAnimationButton)

    var firstInit = true

    init {
        initCollapsingToolbar(view.context, scrollView,
                toolbar, toolbarTitleView, collapsingImageView)
        collapsingImageView.setBackgroundResource(R.drawable.toolbar_placeholder)
    }

    fun bindView(coin: TickerCoin) {
        collapsingImageView.setBackgroundResource(coin.symbolToToolbarId())
        toolbarTitleView.text = coin.formattedName()
        detailsProgressBarContainer.visibility = View.GONE
    }

    fun bindView(starredCoin: StarredCoin?) {
        val wasSelected = favoriteButton.isSelected
        favoriteButton.isSelected = starredCoin != null

        if (firstInit) {
            // Preventing animation when entering view if was already starred
            favoriteButton.pauseAnimation()
            favoriteButton.progress = if (favoriteButton.isSelected) 1F else 0F
            firstInit = false
        } else if (wasSelected != favoriteButton.isSelected) {
            // Starred: animate
            starringChanged()
        }
    }

    private fun starringChanged() {
        // TODO credit: https://www.lottiefiles.com/marvey
        if (favoriteButton.isSelected) {
            // Starred: animate
            favoriteButton.playAnimation()
        } else {
            // Not starred: Set star to position of first frame
            favoriteButton.pauseAnimation()
            favoriteButton.progress = 0F
        }
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
        val TAG = DetailsFragmentViewHolder::class.java.simpleName
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