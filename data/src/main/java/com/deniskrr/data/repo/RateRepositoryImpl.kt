package com.deniskrr.data.repo

import com.deniskrr.data.repo.local.LocalRateDataSource
import com.deniskrr.data.repo.remote.RemoteRateDataSource
import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult
import com.deniskrr.domain.repo.DataSourceType
import com.deniskrr.domain.repo.RateRepository
import com.deniskrr.domain.utils.getCurrencyRateOrBaseRate
import java.math.BigDecimal

class RateRepositoryImpl(
  private val remoteDataSource: RemoteRateDataSource,
  private val localRateDataSource: LocalRateDataSource
) : RateRepository {

  var rates = listOf<Rate>()
  override var currentDataSource: DataSourceType = DataSourceType.LOCAL

  override suspend fun getExchangeRates(baseRate: Rate): RevolutResult<List<Rate>> {
    val result = if (currentDataSource == DataSourceType.REMOTE) {
      remoteDataSource.getExchangeRates(baseRate)
    } else {
      localRateDataSource.getExchangeRates(baseRate)
    }

    return when (result) {
      is RevolutResult.Success -> {
        val ratesMap = result.data

        if (currentDataSource == DataSourceType.REMOTE) {
          localRateDataSource.updateRates(ratesMap)
        }

        // Size <= 1 to accommodate both the case when we have only the base currency and the case when we have no rates at all
        rates = if (rates.size <= 1) {
          populateRatesList(baseRate, ratesMap)
        } else {
          updateRatesList(ratesMap, baseRate)
        }
        RevolutResult.Success(rates)
      }
      is RevolutResult.Error -> {
        RevolutResult.Error(result.errorMessage)
      }
    }
  }

  private fun updateRatesList(
    ratesMap: Map<String, Double>,
    baseRate: Rate
  ): List<Rate> {
    val currentRates = rates.toMutableList()

    return currentRates.map {
      it.copy(
        multiplier = ratesMap.getCurrencyRateOrBaseRate(it.currency.code),
        value = ratesMap.getCurrencyRateOrBaseRate(it.currency.code) * baseRate.value,
      )
    }
  }

  private fun populateRatesList(
    baseRate: Rate,
    ratesMap: Map<String, Double>
  ) = listOf(baseRate) + ratesMap.map { (currencyCode, multiplier) ->
    Rate(
      Currency.forCode(currencyCode),
      multiplier.toBigDecimal(),
      multiplier.toBigDecimal() * baseRate.value
    )
  }

  override suspend fun changeBaseCurrency(position: Int): List<Rate> {
    val currentRates = rates.toMutableList()

    currentRates.removeAt(position).also {
      currentRates.add(0, it)
    }

    rates = currentRates

    return rates
  }

  override suspend fun changeCoefficient(newCoefficient: BigDecimal): List<Rate> {
    val baseRate = rates.first().copy(value = newCoefficient)

    // Manually update list of rates based on stored multipliers.
    // Doing this enables the application to work offline, based on the latest exchange rates retrieved for the current base currency.
    rates = listOf(baseRate) + rates.drop(1).map {
      it.copy(value = it.multiplier * newCoefficient)
    }

    return rates
  }
}