package com.deniskrr.data.repo

import com.deniskrr.data.model.LocalRate
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult
import com.deniskrr.domain.repo.DataSource

open class LocalRateDataSource(private val db: RevolutDatabase) : DataSource {

  override suspend fun getExchangeRates(baseRate: Rate): RevolutResult<Map<String, Double>> {
    return try {
      val list = db.ratesDao().getRates()

      RevolutResult.Success(list.associate { rate -> rate.currencyCode to rate.multiplier })
    } catch (e: Exception) {
      RevolutResult.Error(e.localizedMessage)
    }
  }

  fun updateRates(newRates: Map<String, Double>) {
    val list = newRates.map {
      LocalRate(it.key, it.value)
    }

    db.ratesDao().clean()
    db.ratesDao().insert(*list.toTypedArray())
  }
}