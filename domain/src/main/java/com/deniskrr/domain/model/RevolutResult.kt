package com.deniskrr.domain.model

sealed class RevolutResult<out T> {

  data class Success<out T : Any>(val data: T) : RevolutResult<T>()
  data class Error<T>(val errorMessage: String?) : RevolutResult<T>()
}