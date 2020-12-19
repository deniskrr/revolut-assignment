package com.deniskrr.domain.logger

interface LogWriter {
  /** Log a verbose message */
  fun v(message: String)

  /** Log a verbose exception and a message*/
  fun v(message: String, t: Throwable)

  /** Log a debug message */
  fun d(message: String)

  /** Log a debug exception and a message */
  fun d(message: String, t: Throwable)

  /** Log an info message */
  fun i(message: String)

  /** Log an info exception and a message */
  fun i(message: String, t: Throwable)

  /** Log a warning message */
  fun w(message: String)

  /** Log a warning exception */
  fun w(message: String, t: Throwable)

  /** Log a warning exception.  */
  fun w(t: Throwable)

  /** Log an error message */
  fun e(message: String)

  /** Log an error exception and a message */
  fun e(message: String, t: Throwable)

  /** Log an assert message */
  fun wtf(message: String)

  /** Log an assert exception and a message */
  fun wtf(message: String, t: Throwable)

  /** Log an assert exception.  */
  fun wtf(t: Throwable)

  /** Log at `priority` a message */
  fun log(priority: Int, message: String)
}