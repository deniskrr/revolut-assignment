package com.deniskrr.revoluter.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.setupToolbar(toolbar: Toolbar?) {
  setHasOptionsMenu(true)
  (activity as AppCompatActivity).apply {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
  }
}