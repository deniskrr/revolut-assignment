package com.deniskrr.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class LocalRate(
  @PrimaryKey val currencyCode: String,
  val multiplier: Double
)