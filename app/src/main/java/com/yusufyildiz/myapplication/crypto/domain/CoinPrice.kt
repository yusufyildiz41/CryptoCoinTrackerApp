package com.yusufyildiz.myapplication.crypto.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val price: Double,
    val dateTime: ZonedDateTime
)