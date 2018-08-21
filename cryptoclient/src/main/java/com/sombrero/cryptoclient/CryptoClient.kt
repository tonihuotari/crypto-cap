package com.sombrero.cryptoclient

import android.util.Log
import com.sombrero.cryptoclient.apimodels.ApiCoinListing
import com.sombrero.cryptoclient.apimodels.ApiTickerListing
import com.sombrero.cryptoclient.apimodels.ApiTickerSpecific
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CryptoClient internal constructor() {
    var service: CryptoService? = null

    fun getCoinListing(): Observable<ApiCoinListing> {
        return getClient().getCoinListings()
    }

    fun getTicker10(): Observable<ApiTickerListing> {
        return getClient().getTicker10()
    }

    fun getTickerSpecific(id: Long): Observable<ApiTickerSpecific> {
        return getClient().getTickerSpecific(id)
    }

    private fun getClient(): CryptoService {
        if (service == null) {
            Log.d(TAG, "init crypto client")
            service = newServiceInstance()
        }
        return service!!
    }

    companion object {
        val TAG = CryptoClient::class.java.simpleName

        @Volatile
        private var client: CryptoClient? = null

        fun getInstance(): CryptoClient {
            return client ?: synchronized(this) {
                client ?: CryptoClient().also { client = it }
            }
        }

        private fun newServiceInstance(): CryptoService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.coinmarketcap.com/")
                    .build()

            return retrofit.create(CryptoService::class.java)
        }
    }
}