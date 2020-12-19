package com.deniskrr.domain.repo

import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult
import java.math.BigDecimal

interface RateRepository {

  var currentDataSource: DataSourceType

  suspend fun getExchangeRates(baseRate: Rate): RevolutResult<List<Rate>>
  suspend fun changeBaseCurrency(position: Int): List<Rate>
  suspend fun changeCoefficient(newCoefficient: BigDecimal): List<Rate>
}