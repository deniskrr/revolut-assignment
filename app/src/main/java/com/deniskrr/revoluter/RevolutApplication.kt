package com.deniskrr.revoluter

import android.app.Application
import com.deniskrr.domain.logger.Logger
import com.deniskrr.revoluter.logger.AndroidLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RevolutApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    Logger.initLogger(AndroidLogger())
  }
}