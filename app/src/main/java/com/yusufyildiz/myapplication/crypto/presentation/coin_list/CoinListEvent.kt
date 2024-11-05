package com.yusufyildiz.myapplication.crypto.presentation.coin_list

import com.yusufyildiz.myapplication.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}