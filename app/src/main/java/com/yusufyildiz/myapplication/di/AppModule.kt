package com.yusufyildiz.myapplication.di

import com.yusufyildiz.myapplication.core.data.networking.HttpClientFactory
import com.yusufyildiz.myapplication.crypto.data.networking.RemoteCoinDataSource
import com.yusufyildiz.myapplication.crypto.domain.CoinDataSource
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create())}  // Creates a singleton HTTP client
    single{ RemoteCoinDataSource(get()) }.bind<CoinDataSource>() // Creates a singleton data source
    viewModel{ CoinListViewModel(get()) } // Creates a ViewModel instance
}