package com.deniskrr.data.repo

import com.deniskrr.data.RatesApi
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult
import com.deniskrr.domain.repo.DataSource

open class RemoteRateDataSource(private val api: RatesApi) : DataSource {

  override suspend fun getExchangeRates(baseRate: Rate): RevolutResult<Map<String, Double>> {

    val response = try {
      api.getExchangeRates(baseRate.currency.code)
    } catch (e: Exception) {
      return RevolutResult.Error(e.localizedMessage)
    }

    return if (response.isSuccessful) {
      val currencies = response.body()

      RevolutResult.Success(currencies?.rates ?: mapOf())
    } else {
      RevolutResult.Error("Cannot retrieve exchange rates")
    }
  }
}