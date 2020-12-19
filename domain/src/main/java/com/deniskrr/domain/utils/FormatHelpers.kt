package com.deniskrr.domain.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun getBigDecimalFormat(): DecimalFormat {
  return (NumberFormat.getNumberInstance(Locale.getDefault()) as DecimalFormat).apply {
    isGroupingUsed = true
    isParseBigDecimal = true
    maximumFractionDigits = 2
    minimumFractionDigits = 2
  }
}