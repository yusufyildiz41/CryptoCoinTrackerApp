package com.yusufyildiz.myapplication.crypto.presentation.coin_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufyildiz.myapplication.core.domain.util.onError
import com.yusufyildiz.myapplication.core.domain.util.onSuccess
import com.yusufyildiz.myapplication.crypto.domain.CoinDataSource
import com.yusufyildiz.myapplication.crypto.presentation.coin_detail.DataPoint
import com.yusufyildiz.myapplication.crypto.presentation.models.CoinUi
import com.yusufyildiz.myapplication.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())

    val state = _state
        .onStart { loadCoins() } // onStart triggers loadCoins() when collection starts
        // stateIn converts the Flow to a StateFlow with specific sharing behavior
        .stateIn(
            viewModelScope,// Coroutine scope tied to ViewModel lifecycle
            SharingStarted.WhileSubscribed(5000L),// Only keeps the subscription active for 5 seconds after last subscriber
            CoinListState() // Initial state
        )

    /**
     * Use Channel for one-time events (errors, navigation, toast messages)
     * Use StateFlow for UI state that needs to be maintained (data to display)
     */
    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }

            is CoinListAction.OnRefresh -> {
                loadCoins()
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess { history ->
                val dataPoints = history
                    .sortedBy { it.dateTime }
                    .map {
                        DataPoint(
                            x = it.dateTime.hour.toFloat(),
                            y = it.price.toFloat(),
                            xLabel = DateTimeFormatter
                                .ofPattern("ha\nM/d")
                                .format(it.dateTime)
                        )
                    }

                _state.update {
                    it.copy(
                        selectedCoin = it.selectedCoin?.copy(
                            coinPriceHistory = dataPoints
                        )
                    )
                }
            }.onError {
                _events.send(CoinListEvent.Error(it))
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { coin -> coin.toCoinUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

}