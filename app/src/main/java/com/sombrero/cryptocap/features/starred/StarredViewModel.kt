package com.sombrero.cryptocap.features.starred

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.sombrero.cryptomodel.starred.StarredCoin
import com.sombrero.cryptomodel.starred.StarredCoinRepository

class StarredViewModel(repository: StarredCoinRepository) : ViewModel() {
    private val starredCoins = MediatorLiveData<List<StarredCoin>>()

    init {
        starredCoins.addSource(repository.getStarredCoins(), starredCoins::setValue)
    }

    fun getStarredCoins() = starredCoins
}