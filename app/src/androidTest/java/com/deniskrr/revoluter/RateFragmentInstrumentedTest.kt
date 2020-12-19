package com.deniskrr.revoluter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deniskrr.data.di.NetworkModule
import com.deniskrr.domain.logger.Logger
import com.deniskrr.domain.model.Currency
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult
import com.deniskrr.domain.repo.DataSourceType
import com.deniskrr.domain.repo.RateRepository
import com.deniskrr.revoluter.logger.AndroidLogger
import com.deniskrr.revoluter.ui.RatesFragment
import com.nhaarman.mockitokotlin2.*
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
@RunWith(AndroidJUnit4::class)
class RateFragmentInstrumentedTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @BindValue
  lateinit var repository: RateRepository

  @Before
  fun setup() {
    hiltRule.inject()

    repository = mock {
      onBlocking { getExchangeRates(any()) } doReturn (
          RevolutResult.Success(
            getMultipleRatesMockData()
          ))

      onBlocking { changeBaseCurrency(any()) } doReturn getMultipleRatesMockData()

      onBlocking { changeCoefficient(any()) } doReturn getMultipleRatesMockData()
    }

    Logger.initLogger(AndroidLogger())
  }

  /**
   * Rates are displayed with two decimals, rounded up
   */
  @Test
  fun rateFragmentRecyclerView_displaysRatesRoundedUp() {
    launchFragmentInHiltContainer<RatesFragment>()

    onView(withText("1.00")).check(matches(isDisplayed()))
    onView(withText("1.59")).check(matches(isDisplayed()))
    onView(withText("1.97")).check(matches(isDisplayed()))
    onView(withText("4.20")).check(matches(isDisplayed()))
  }

  /**
   * Base currency is not changed when data source is [DataSourceType.LOCAL]
   */
  @Test
  fun rateFragmentRecyclerView_tapValueChangesBaseCurrency() {
    launchFragmentInHiltContainer<RatesFragment>()

    onView(withText("1.97")).perform(click())

    verifyBlocking(repository, times(0)) {
      this.changeBaseCurrency(2)
    }
  }

  @Test
  fun rateFragmentRecyclerView_currencyLongNameIsShown() {
    launchFragmentInHiltContainer<RatesFragment>()

    onView(withText(Currency.EUR.longName)).check(matches(isDisplayed()))
  }

  @Test
  fun rateFragmentRecyclerView_coefficientIsUpdated() {
    launchFragmentInHiltContainer<RatesFragment>()

    onView(withText("1.00")).perform(typeText("111"))

    // Called once for the initial coefficient and then 3 times for each character in '111'
    verifyBlocking(repository, times(4)) {
      this.changeCoefficient(any())
    }
  }

  private fun getMultipleRatesMockData() = listOf(
    Rate(Currency.EUR, 1.toBigDecimal(), 1.toBigDecimal()),
    Rate(Currency.AUD, 1.59.toBigDecimal(), 1.59.toBigDecimal()),
    Rate(Currency.BGN, 1.968.toBigDecimal(), 1.968.toBigDecimal()),
    Rate(Currency.BRL, 4.203.toBigDecimal(), 4.203.toBigDecimal()),
  )
}