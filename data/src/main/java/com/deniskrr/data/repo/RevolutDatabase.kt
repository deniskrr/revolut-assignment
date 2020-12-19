package com.deniskrr.data.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deniskrr.data.RatesDao
import com.deniskrr.data.model.LocalRate

@Database(entities = [LocalRate::class], version = 1)
abstract class RevolutDatabase : RoomDatabase() {
  abstract fun ratesDao(): RatesDao
}