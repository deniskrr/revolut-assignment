package com.deniskrr.data.model

data class ExchangeRates(
  val baseCurrency: String,
  val rates: Map<String, Double>
)