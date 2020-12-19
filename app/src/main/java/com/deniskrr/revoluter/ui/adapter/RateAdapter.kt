package com.deniskrr.revoluter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.deniskrr.domain.model.Rate
import com.deniskrr.revoluter.databinding.ViewHolderRateBinding
import java.math.BigDecimal

class RateAdapter(
  private val changeBaseCurrencyHandler: (Int) -> Unit,
  private val changeCoefficientHandler: (BigDecimal) -> Unit
) :
  ListAdapter<Rate, RateViewHolder>(RateDiff) {

  companion object RateDiff : DiffUtil.ItemCallback<Rate>() {
    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean =
      oldItem.currency == newItem.currency

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean =
      oldItem.value == newItem.value

    override fun getChangePayload(oldItem: Rate, newItem: Rate): RatePayload =
      RatePayload(newItem.value)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
    val binding = ViewHolderRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return RateViewHolder(binding)
  }

  override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
    holder.bind(getItem(position), changeBaseCurrencyHandler, changeCoefficientHandler)
  }

  override fun onBindViewHolder(holder: RateViewHolder, position: Int, payloads: MutableList<Any>) {
    if (payloads.isNotEmpty()) {
      holder.bindPayload(payloads.first() as RatePayload)
    } else {
      super.onBindViewHolder(holder, position, payloads)
    }
  }
}

