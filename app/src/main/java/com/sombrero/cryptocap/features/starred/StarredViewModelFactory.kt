package com.sombrero.cryptocap.features.starred

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sombrero.cryptomodel.starred.StarredCoinRepository

class StarredViewModelFactory(
        private val starredCoinRepository: StarredCoinRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = StarredViewModel(starredCoinRepository) as T
}