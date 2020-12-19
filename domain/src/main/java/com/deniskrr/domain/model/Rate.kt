package com.deniskrr.domain.model

import java.math.BigDecimal

data class Rate(val currency: Currency, val multiplier: BigDecimal, val value: BigDecimal)