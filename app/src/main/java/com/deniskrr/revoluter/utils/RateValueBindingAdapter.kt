package com.deniskrr.revoluter.utils

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.deniskrr.domain.utils.getBigDecimalFormat
import java.math.BigDecimal

@BindingAdapter("amount")
fun setRateAmount(editText: EditText, amount: BigDecimal) {
  editText.setText(getBigDecimalFormat().format(amount))
}