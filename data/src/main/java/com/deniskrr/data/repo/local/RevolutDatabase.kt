package com.deniskrr.data.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deniskrr.data.repo.local.RatesDao
import com.deniskrr.data.model.LocalRate

@Database(entities = [LocalRate::class], version = 1)
abstract class RevolutDatabase : RoomDatabase() {
  abstract fun ratesDao(): RatesDao
}