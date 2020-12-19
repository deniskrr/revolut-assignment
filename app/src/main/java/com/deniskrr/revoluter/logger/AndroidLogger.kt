package com.deniskrr.revoluter.logger

import android.util.Log
import com.deniskrr.domain.logger.LogWriter
import com.deniskrr.domain.logger.Logger

class AndroidLogger : LogWriter {

  private val ignoreClasses = listOf(
    AndroidLogger::class.java.name,
    Logger::class.java.name,
    LogWriter::class.java.name,
    Logger::class.java.name,
    Logger.Companion::class.java.name
  )

  private val tag: String
    get() = Throwable().stackTrace
      .first { it.className !in ignoreClasses }
      .let(::createStackElementTag)

  private fun createStackElementTag(element: StackTraceElement): String =
    element.className.substringAfterLast('.')

  override fun v(message: String) {
    Log.v(tag, message)
  }

  override fun v(message: String, t: Throwable) {
    Log.v(tag, message, t)
  }

  override fun d(message: String) {
    Log.d(tag, message)
  }

  override fun d(message: String, t: Throwable) {
    Log.d(tag, message, t)
  }

  override fun i(message: String) {
    Log.i(tag, message)
  }

  override fun i(message: String, t: Throwable) {
    Log.i(tag, message, t)
  }

  override fun w(message: String) {
    Log.w(tag, message)
  }

  override fun w(message: String, t: Throwable) {
    Log.w(tag, message, t)
  }

  override fun w(t: Throwable) {
    Log.w(tag, t)
  }

  override fun e(message: String) {
    Log.e(tag, message)
  }

  override fun e(message: String, t: Throwable) {
    Log.e(tag, message, t)
  }

  override fun wtf(message: String) {
    Log.wtf(tag, message)
  }

  override fun wtf(message: String, t: Throwable) {
    Log.wtf(tag, message, t)
  }

  override fun wtf(t: Throwable) {
    Log.wtf(tag, t)
  }

  override fun log(priority: Int, message: String) {
    Log.println(priority, tag, message)
  }
}