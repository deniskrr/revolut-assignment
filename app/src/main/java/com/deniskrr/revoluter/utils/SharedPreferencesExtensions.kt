package com.deniskrr.revoluter.utils

import android.content.SharedPreferences
import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.utils.getBaseRateWithCoefficient

const val baseCurrencyKey = "baseCurrency"

fun SharedPreferences.getBaseRate() = Currency.forCode(
  this.getString(
    baseCurrencyKey,
    Currency.EUR.code
  )!!
).getBaseRateWithCoefficient(1.toBigDecimal())

fun SharedPreferences.setBaseRate(baseRate: Rate) =
  this.edit().putString(baseCurrencyKey, baseRate.currency.code).apply()