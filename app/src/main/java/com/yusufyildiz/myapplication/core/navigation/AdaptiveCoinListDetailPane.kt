package com.yusufyildiz.myapplication.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import com.plcoding.cryptotracker.core.presentation.util.toString
import com.yusufyildiz.myapplication.crypto.presentation.coin_detail.CoinDetailScreen
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListAction
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListEvent
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListScreen
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    ObserveAsEvents(
        events = viewModel.events,
        onEvent = { event ->
            when(event){
                is CoinListEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.error.toString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    )


    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        navigator= navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when(action){
                            is CoinListAction.OnCoinClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                            is CoinListAction.OnRefresh -> {
                                viewModel.onAction(action)
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state)
            }
        },
        modifier = modifier
    )

}