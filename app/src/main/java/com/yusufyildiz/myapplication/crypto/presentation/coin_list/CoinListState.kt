package com.yusufyildiz.myapplication.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.yusufyildiz.myapplication.crypto.presentation.models.CoinUi


@Immutable//immutable tells the compiler that the data class itself and never change
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)