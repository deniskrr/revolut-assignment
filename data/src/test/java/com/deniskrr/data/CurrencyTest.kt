package com.deniskrr.data

import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.utils.getBaseRateWithCoefficient
import org.junit.Assert.assertEquals
import org.junit.Test


class CurrencyTest {

  @Test
  fun forCode_returnsCorrectCurrency() {
    val currencyCode = "EUR"

    assertEquals(Currency.forCode(currencyCode), Currency.EUR)
  }

  @Test
  fun getBaseRateWithCoefficient_returnsCorrectRate() {
    val currency = Currency.EUR
    val coefficient = 42.toBigDecimal()

    assertEquals(
      currency.getBaseRateWithCoefficient(coefficient),
      Rate(currency, 1.toBigDecimal(), coefficient)
    )
  }
}