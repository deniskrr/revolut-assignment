package com.deniskrr.domain.utils

import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.Rate
import java.math.BigDecimal

fun Currency.getBaseRateWithCoefficient(coefficient: BigDecimal): Rate =
  Rate(this, 1.toBigDecimal(), coefficient)

fun Map<String, Double>.getCurrencyRateOrBaseRate(
  currencyCode: String
) = this.getOrElse(currencyCode) { 1.0 }.toBigDecimal()