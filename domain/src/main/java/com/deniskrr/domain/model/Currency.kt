package com.deniskrr.domain.model

enum class Currency(val longName: String) {
  AUD("Australian dollar"), // Australia
  BGN("Bulgarian lev"), // Bulgaria
  BRL("Brazilian real"), // Brazil
  CAD("Canadian dollar"), // Canada
  CHF("Swiss franc"), // Switzerland
  CNY("Chinese yuan"), // China
  CZK("Czech koruna"), // Czech Republic
  DKK("Danish krone"), // Denmark
  EUR("Euro"), // European Union
  GBP("British pound"), // UK
  HKD("Hong Kong dollar"), // Hong Kong
  HRK("Croatian kuna"), // Croatia
  HUF("Hungarian forint"), // Hungary
  IDR("Indonesian rupiah"), // Indonesia
  ILS("Israeli new shekel"), // Israel
  INR("Indian rupee"), // India
  ISK("Icelandic króna"), // Iceland
  JPY("Japanese yen"), // Japan
  KRW("South Korean won"), // South Korea
  MXN("Mexican peso"), // Mexico
  MYR("Malaysian ringgit"), // Malaysia
  NOK("Norwegian krone"), // Norway
  NZD("New Zealand dollar"), // New Zealand
  PHP("Philippine peso"), // Philippines
  PLN("Polish złoty"), // Poland
  RON("Romanian leu"), // Romania
  RUB("Russian ruble"), // Russia
  SEK("Swedish krona"), // Sweden
  SGD("Singapore dollar"), // Singapore
  THB("Thai baht"), // Thailand
  USD("United States dollar"), // United States
  ZAR("South African rand"), // South Africa
  UNKNOWN("UNKNOWN");

  val code: String
    get() = name

  companion object {
    fun forCode(code: String): Currency {
      return values().find { it.code == code } ?: UNKNOWN
    }
  }
}