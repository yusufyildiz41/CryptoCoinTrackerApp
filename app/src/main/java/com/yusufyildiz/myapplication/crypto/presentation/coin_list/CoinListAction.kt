package com.yusufyildiz.myapplication.crypto.presentation.coin_list

import com.yusufyildiz.myapplication.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
    data object OnRefresh: CoinListAction
}