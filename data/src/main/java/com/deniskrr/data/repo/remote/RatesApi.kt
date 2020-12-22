package com.deniskrr.data.repo.remote

import com.deniskrr.data.model.ExchangeRates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

  @GET("/api/android/latest")
  suspend fun getExchangeRates(
    @Query("base") currencyCode: String,
  ): Response<ExchangeRates>
}