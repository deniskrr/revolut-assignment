package com.deniskrr.revoluter.ui.rates

import android.app.Application
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.deniskrr.domain.logger.Logger
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.model.RevolutResult
import com.deniskrr.domain.repo.DataSourceType
import com.deniskrr.domain.repo.RateRepository
import com.deniskrr.revoluter.utils.SingleLiveEvent
import com.deniskrr.revoluter.utils.getBaseRate
import com.deniskrr.revoluter.utils.setBaseRate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.math.BigDecimal

class RatesViewModel @ViewModelInject constructor(
  @ApplicationContext application: Context,
  private val repo: RateRepository
) : AndroidViewModel(application as Application) {

  val cannotChangeBaseCurrencyEvent = SingleLiveEvent<Void>()
  private val prefs =
    getApplication<Application>().getSharedPreferences("BaseCurrencyPrefs", Context.MODE_PRIVATE)

  val rates = MutableLiveData(listOf<Rate>())
  private var baseRate = prefs.getBaseRate()

  private var currentJob: Job? = null
  private var hasLocalData: Boolean = false
  private val shouldRetrieveData: Boolean
    get() = !hasLocalData || repo.currentDataSource == DataSourceType.REMOTE

  fun getRatesPeriodically() {
    currentJob = viewModelScope.launch(Dispatchers.IO) {
      while (true) {
        if (shouldRetrieveData) {
          Logger.d("Getting rates")
          getRates()
        } else {
          Logger.d("Offline and up-to-date data. Nothing to do.")
        }
        delay(1000)
      }
    }
  }

  private suspend fun getRates() {
    when (val result = repo.getExchangeRates(baseRate)) {
      is RevolutResult.Success -> {
        withContext(Dispatchers.Main) {
          if (isActive) {
            Logger.Companion.i("Updating rates for ${baseRate.currency.longName}")
            rates.value = result.data
            if (repo.currentDataSource == DataSourceType.LOCAL) {
              hasLocalData = true
            } else {
              prefs.setBaseRate(baseRate)
            }
          }
        }
      }
      is RevolutResult.Error -> {
        result.errorMessage?.let {
          Logger.e(it)
        }
      }
    }
  }

  fun changeBaseCurrency(position: Int) {
    if (repo.currentDataSource == DataSourceType.REMOTE) {
      currentJob?.cancel()

      viewModelScope.launch(Dispatchers.IO) {
        repo.changeBaseCurrency(position).also {
          withContext(Dispatchers.Main) {
            Logger.Companion.i("Changing the base currency to ${baseRate.currency.longName}")
            rates.value = it
            baseRate = rates.value!!.first()
          }
        }
      }

      getRatesPeriodically()
    } else if (position != 0) {
      cannotChangeBaseCurrencyEvent.call()
    }
  }

  fun changeCoefficient(newCoefficient: BigDecimal) {
    viewModelScope.launch(Dispatchers.IO) {
      repo.changeCoefficient(newCoefficient).also {
        withContext(Dispatchers.Main) {
          Logger.Companion.i("Changing the base currency coefficient to ${baseRate.value}")
          rates.value = it
          baseRate = it.first()
        }
      }
    }
  }

  fun changeRatesDataSourceType(newDataSourceType: DataSourceType) {
    Logger.Companion.d("Changing data source type to: ${newDataSourceType.name}")

    repo.currentDataSource = newDataSourceType
  }
}