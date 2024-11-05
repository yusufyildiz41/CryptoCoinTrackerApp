package com.yusufyildiz.myapplication.crypto.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.yusufyildiz.myapplication.crypto.data.networking.dto.CoinDto
import com.yusufyildiz.myapplication.crypto.data.networking.dto.CoinPriceDto
import com.yusufyildiz.myapplication.crypto.domain.Coin
import com.yusufyildiz.myapplication.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}


fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        price = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}