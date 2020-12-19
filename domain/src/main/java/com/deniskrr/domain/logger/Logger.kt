package com.deniskrr.domain.logger


abstract class Logger {

  companion object : LogWriter {
    private lateinit var logWriter: LogWriter

    fun initLogger(logWriter: LogWriter) {
      Companion.logWriter = logWriter
    }

    override fun v(message: String) {
      logWriter.v(message)
    }

    override fun v(message: String, t: Throwable) {
      logWriter.v(message, t)
    }

    override fun d(message: String) {
      logWriter.d(message)
    }

    override fun d(message: String, t: Throwable) {
      logWriter.d(message, t)
    }

    override fun i(message: String) {
      logWriter.i(message)
    }

    override fun i(message: String, t: Throwable) {
      logWriter.i(message, t)
    }

    override fun w(message: String) {
      logWriter.w(message)
    }

    override fun w(message: String, t: Throwable) {
      logWriter.w(message, t)
    }

    override fun w(t: Throwable) {
      logWriter.w(t)
    }

    override fun e(message: String) {
      logWriter.e(message)
    }

    override fun e(message: String, t: Throwable) {
      logWriter.e(message, t)
    }

    override fun wtf(message: String) {
      logWriter.wtf(message)
    }

    override fun wtf(message: String, t: Throwable) {
      logWriter.wtf(message, t)
    }

    override fun wtf(t: Throwable) {
      logWriter.wtf(t)
    }

    override fun log(priority: Int, message: String) {
      logWriter.log(priority, message)
    }
  }
}