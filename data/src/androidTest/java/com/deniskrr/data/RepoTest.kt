package com.deniskrr.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deniskrr.data.repo.LocalRateDataSource
import com.deniskrr.data.repo.RateRepositoryImpl
import com.deniskrr.data.repo.RemoteRateDataSource
import com.deniskrr.data.repo.RevolutDatabase
import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult
import com.deniskrr.domain.repo.DataSourceType
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RepoTest {

  private lateinit var db: RevolutDatabase
  private lateinit var remoteDataSource: RemoteRateDataSource
  private lateinit var localRateDataSource: LocalRateDataSource
  private lateinit var repository: RateRepositoryImpl
  private val baseRate = Rate(Currency.EUR, 1.toBigDecimal(), 1.toBigDecimal())

  @Before
  fun initDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(
      context, RevolutDatabase::class.java
    ).build()

    remoteDataSource = mock {
      onBlocking { getExchangeRates(baseRate) } doReturn (RevolutResult.Success(
        mapOf(
          "AUD" to 1.59,
          "BGN" to 1.968,
          "BRL" to 4.203
        )
      ))
    }

    localRateDataSource = LocalRateDataSource(db)

    repository = RateRepositoryImpl(remoteDataSource, localRateDataSource).apply {
      currentDataSource = DataSourceType.REMOTE
    }
  }

  @Test
  fun getExchangeRates_returnsCorrectList() {
    runBlocking {
      val result = repository.getExchangeRates(baseRate) as RevolutResult.Success
      assertEquals(
        result.data, mockData()
      )
    }
  }

  @Test
  fun changeBaseCurrency_returnsCorrectList() {
    runBlocking {
      val newBaseCurrencyPosition = 2
      repository.rates = mockData()

      val rates = repository.changeBaseCurrency(newBaseCurrencyPosition)

      val newList = mockData().toMutableList()
      newList.removeAt(newBaseCurrencyPosition).also {
        newList.add(0, it)
      }

      assertEquals(
        rates,
        newList
      )
    }
  }


  private fun mockData() = listOf(
    Rate(Currency.EUR, 1.toBigDecimal(), 1.toBigDecimal()),
    Rate(Currency.AUD, 1.59.toBigDecimal(), 1.59.toBigDecimal()),
    Rate(Currency.BGN, 1.968.toBigDecimal(), 1.968.toBigDecimal()),
    Rate(Currency.BRL, 4.203.toBigDecimal(), 4.203.toBigDecimal()),
  )
}