package com.deniskrr.revoluter.utils

import com.deniskrr.domain.model.Currency
import com.deniskrr.revoluter.R

fun Currency.getDrawableResId(): Int {
  return when (this) {
    Currency.AUD -> R.drawable.flag_australia
    Currency.BGN -> R.drawable.flag_bulgaria
    Currency.BRL -> R.drawable.flag_brazil
    Currency.CAD -> R.drawable.flag_canada
    Currency.CHF -> R.drawable.flag_switzerland
    Currency.CNY -> R.drawable.flag_china
    Currency.CZK -> R.drawable.flag_czech_republic
    Currency.DKK -> R.drawable.flag_denmark
    Currency.EUR -> R.drawable.flag_european_union
    Currency.GBP -> R.drawable.flag_uk
    Currency.HKD -> R.drawable.flag_hong_kong
    Currency.HRK -> R.drawable.flag_croatia
    Currency.HUF -> R.drawable.flag_hungary
    Currency.IDR -> R.drawable.flag_indonesia
    Currency.ILS -> R.drawable.flag_israel
    Currency.INR -> R.drawable.flag_india
    Currency.ISK -> R.drawable.flag_iceland
    Currency.JPY -> R.drawable.flag_japan
    Currency.KRW -> R.drawable.flag_south_korea
    Currency.MXN -> R.drawable.flag_mexico
    Currency.MYR -> R.drawable.flag_malaysia
    Currency.NOK -> R.drawable.flag_norway
    Currency.NZD -> R.drawable.flag_new_zealand
    Currency.PHP -> R.drawable.flag_philippines
    Currency.PLN -> R.drawable.flag_poland
    Currency.RON -> R.drawable.flag_romania
    Currency.RUB -> R.drawable.flag_russia
    Currency.SEK -> R.drawable.flag_sweden
    Currency.SGD -> R.drawable.flag_singapore
    Currency.THB -> R.drawable.flag_thailand
    Currency.USD -> R.drawable.flag_us
    Currency.ZAR -> R.drawable.flag_south_africa
    // TODO: Add a placeholder flag
    Currency.UNKNOWN -> R.drawable.flag_romania
  }
}