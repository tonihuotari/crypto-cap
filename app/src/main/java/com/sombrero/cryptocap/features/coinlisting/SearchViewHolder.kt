package com.sombrero.cryptocap.features.coinlisting

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sombrero.cryptocap.R

class SearchViewHolder private constructor(
        view: View,
        lifecycleOwner: LifecycleOwner,
        minimizedMargin: Int
) {
    private val constraintLayout: ConstraintLayout = view.findViewById(R.id.searchConstraintLayout)
    private val searchView: SearchView = view.findViewById(R.id.searchSearchView)
    private val backgroundView: ViewGroup = view.findViewById(R.id.searchBackgroundView)

    init {
        searchView.setOnQueryTextFocusChangeListener { _, focused ->
            // Only animate if we are in a resumed state
            val animate = lifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED
            val margin: Int = if (!focused) minimizedMargin else 0

            setSearchViewConstraints(
                    constraintLayout,
                    backgroundView,
                    searchView,
                    margin = margin,
                    animate = animate
            )
        }

        searchView.setIconifiedByDefault(false)

        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun clearFocus() {
                // Clearing focus when leaving the view in order to prevent keyboard from showing up automatically when returning
                searchView.clearFocus()
            }
        })
    }

    fun setOnQueryTextListener(listener: SearchView.OnQueryTextListener) {
        searchView.setOnQueryTextListener(listener)
    }

    companion object {
        val TAG = SearchViewHolder::class.java.simpleName

        private fun setSearchViewConstraints(
                constraintLayout: ConstraintLayout,
                backgroundView: ViewGroup,
                searchView: SearchView,
                margin: Int,
                animate: Boolean) {

            val constraint = ConstraintSet()
            constraint.clone(constraintLayout)
            constraint.connect(backgroundView.id, ConstraintSet.START, searchView.id, ConstraintSet.START, margin)
            constraint.connect(backgroundView.id, ConstraintSet.END, searchView.id, ConstraintSet.END, margin)
            constraint.connect(backgroundView.id, ConstraintSet.BOTTOM, searchView.id, ConstraintSet.BOTTOM, margin)
            constraint.connect(backgroundView.id, ConstraintSet.TOP, searchView.id, ConstraintSet.TOP, margin)

            if (animate) {
                TransitionManager.beginDelayedTransition(backgroundView)
            }

            constraint.applyTo(constraintLayout)
        }

        fun newInstance(parent: ViewGroup,
                        lifecycleOwner: LifecycleOwner,
                        attachToRoot: Boolean = true): SearchViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_search, parent, attachToRoot)
            val minimizedMargin = parent.context.resources.getDimension(R.dimen.search_box_margin).toInt()
            return SearchViewHolder(view, lifecycleOwner, minimizedMargin)
        }
    }
}