package com.deniskrr.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deniskrr.data.model.LocalRate

@Dao
interface RatesDao {

  @Query("SELECT * FROM rates")
  fun getRates(): List<LocalRate>

  @Insert
  fun insert(vararg rates: LocalRate)

  @Query("DELETE FROM rates")
  fun clean()

}