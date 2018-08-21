package com.sombrero.cryptoclient

import com.sombrero.cryptoclient.apimodels.ApiCoinListing
import com.sombrero.cryptoclient.apimodels.ApiTickerListing
import com.sombrero.cryptoclient.apimodels.ApiTickerSpecific
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoService {
    @GET("v2/listings")
    fun getCoinListings(): Observable<ApiCoinListing>

    @GET("v2/ticker/?limit=10")
    fun getTicker10(): Observable<ApiTickerListing>

    @GET("v2/ticker/{id}")
    fun getTickerSpecific(@Path("id") id: Long): Observable<ApiTickerSpecific>

}