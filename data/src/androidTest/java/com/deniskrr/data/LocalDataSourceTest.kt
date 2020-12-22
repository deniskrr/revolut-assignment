package com.deniskrr.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deniskrr.data.repo.local.LocalRateDataSource
import com.deniskrr.data.repo.local.RevolutDatabase
import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.RevolutResult
import com.deniskrr.domain.utils.getBaseRateWithCoefficient
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {

  private lateinit var db: RevolutDatabase
  private lateinit var dataSource: LocalRateDataSource

  @Before
  fun initDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(
      context, RevolutDatabase::class.java
    ).build()

    dataSource = LocalRateDataSource(db)
  }

  @Test
  fun getExchangeRates_returnsCorrectList() {
    runBlocking {
      dataSource.updateRates(
        getMockData()
      )

      val ratesResult =
        dataSource.getExchangeRates(Currency.EUR.getBaseRateWithCoefficient(1.toBigDecimal()))

      if (ratesResult is RevolutResult.Success) {
        val rates = ratesResult.data

        assertEquals(getMockData(), rates)
      } else {
        assertTrue(false)
      }
    }
  }

  private fun getMockData() = mapOf(
    "AUD" to 1.59,
    "BGN" to 1.968,
    "BRL" to 4.203
  )
}