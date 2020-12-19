package com.deniskrr.domain

import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.RevolutResult

interface RateService {

  suspend fun getExchangeRates(currency: Currency): RevolutResult<Map<String, Double>>
}