package com.sombrero.cryptocap.common

import android.content.Context
import com.sombrero.cryptocap.features.coindetails.DetailsViewModelFactory
import com.sombrero.cryptocap.features.coinlisting.CoinListingViewModelFactory
import com.sombrero.cryptocap.features.starred.StarredViewModelFactory
import com.sombrero.cryptocap.features.ticker.TickerViewModelFactory
import com.sombrero.cryptoclient.CryptoClient
import com.sombrero.cryptomodel.AppDatabase
import com.sombrero.cryptomodel.coin.CoinRepository
import com.sombrero.cryptomodel.starred.StarredCoinRepository
import com.sombrero.cryptomodel.ticker.TickerCoinRepository

object InjectorUtils {
    private fun getCoinRepository(context: Context): CoinRepository {
        return CoinRepository.getInstance(
                AppDatabase.getInstance(context).coinDao(),
                CryptoClient.getInstance())
    }

    private fun getTickerRepository(context: Context): TickerCoinRepository {
        return TickerCoinRepository.getInstance(
                AppDatabase.getInstance(context).tickerDao(),
                CryptoClient.getInstance())
    }

    private fun getStarredRepository(context: Context): StarredCoinRepository {
        return StarredCoinRepository.getInstance(
                AppDatabase.getInstance(context).starredDao()
        )
    }

    fun provideCoinListingViewModelFactory(context: Context): CoinListingViewModelFactory {
        val repository = getCoinRepository(context)
        return CoinListingViewModelFactory(repository)
    }

    fun provideTickerViewModelFactory(context: Context): TickerViewModelFactory {
        val repository = getTickerRepository(context)
        return TickerViewModelFactory(repository)
    }

    fun provideDetailsViewModelFactory(context: Context, coinId: Long): DetailsViewModelFactory {
        val repository = getTickerRepository(context)
        val starredRepository = getStarredRepository(context)
        return DetailsViewModelFactory(coinId, repository, starredRepository)
    }

    fun provideStarredViewModelFactory(context: Context): StarredViewModelFactory {
        val repository = getStarredRepository(context)
        return StarredViewModelFactory(repository)
    }

}