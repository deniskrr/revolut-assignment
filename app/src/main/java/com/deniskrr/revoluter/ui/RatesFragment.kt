package com.deniskrr.revoluter.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deniskrr.domain.logger.Logger
import com.deniskrr.domain.repo.DataSourceType
import com.deniskrr.revoluter.R
import com.deniskrr.revoluter.databinding.RatesFragmentBinding
import com.deniskrr.revoluter.ui.adapter.RateAdapter
import com.deniskrr.revoluter.utils.setupToolbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatesFragment : Fragment() {

  private lateinit var adapter: RateAdapter
  private val viewModel: RatesViewModel by viewModels()
  private lateinit var binding: RatesFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = RatesFragmentBinding.inflate(inflater)
    binding.lifecycleOwner = viewLifecycleOwner
    setupToolbar(binding.toolbar)

    adapter = RateAdapter(viewModel::changeBaseCurrency, viewModel::changeCoefficient)

    binding.currencyRv.adapter = adapter

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.rates.observe(viewLifecycleOwner) {
      adapter.submitList(it)
    }

    viewModel.getRatesPeriodically()

    handleDataSourceTypeBasedOnConnectivity()

    viewModel.cannotChangeBaseCurrencyEvent.observe(viewLifecycleOwner) {
      Snackbar.make(binding.root, R.string.cant_change_base_currency, Snackbar.LENGTH_SHORT)
        .show()
    }
  }

  private fun handleDataSourceTypeBasedOnConnectivity() {
    val networkCallback: NetworkCallback = object : NetworkCallback() {
      override fun onAvailable(network: Network) {
        Logger.Companion.d("Got a connection")

        viewModel.changeRatesDataSourceType(DataSourceType.REMOTE)
      }

      override fun onLost(network: Network) {
        Logger.Companion.d("Lost the connection")

        viewModel.changeRatesDataSourceType(DataSourceType.LOCAL)
      }
    }

    val connectivityManager =
      requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    connectivityManager.registerDefaultNetworkCallback(networkCallback)
  }

}