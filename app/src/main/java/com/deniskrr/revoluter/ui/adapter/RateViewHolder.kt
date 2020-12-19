package com.deniskrr.revoluter.ui.adapter

import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.deniskrr.domain.model.Rate
import com.deniskrr.domain.utils.getBigDecimalFormat
import com.deniskrr.revoluter.databinding.ViewHolderRateBinding
import com.deniskrr.revoluter.utils.getDrawableResId
import java.math.BigDecimal
import java.text.ParseException

class RateViewHolder(private val binding: ViewHolderRateBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun bind(
    rate: Rate,
    changeBaseCurrencyHandler: (Int) -> Unit,
    changeCoefficientHandler: (BigDecimal) -> Unit
  ) {

    binding.rate = rate
    binding.countryIv.setImageResource(rate.currency.getDrawableResId())

    binding.valueEt.setOnFocusChangeListener { v, hasFocus ->
      if (hasFocus) {
        changeBaseCurrencyHandler(adapterPosition)
      }
    }

    binding.valueEt.doAfterTextChanged {
      if (adapterPosition == 0) {
        val newCoefficient = try {
          (getBigDecimalFormat().parse(it.toString()) as BigDecimal)
        } catch (e: ParseException) {
          0.toBigDecimal()
        }

        changeCoefficientHandler(
          newCoefficient
        )
      }
    }
  }

  fun bindPayload(payload: RatePayload) {
    if (adapterPosition != 0) {
      binding.rate = binding.rate?.copy(value = payload.value)
    }
    binding.executePendingBindings()
  }
}


