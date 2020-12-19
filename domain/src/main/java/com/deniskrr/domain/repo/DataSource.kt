package com.deniskrr.domain.repo

import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult

interface DataSource {

  suspend fun getExchangeRates(baseRate: Rate): RevolutResult<Map<String, Double>>

}