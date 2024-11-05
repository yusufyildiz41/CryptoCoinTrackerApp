package com.yusufyildiz.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import com.plcoding.cryptotracker.core.presentation.util.toString
import com.yusufyildiz.myapplication.core.navigation.AdaptiveCoinListDetailPane
import com.yusufyildiz.myapplication.crypto.presentation.coin_detail.CoinDetailScreen
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListEvent
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListScreen
import com.yusufyildiz.myapplication.crypto.presentation.coin_list.CoinListViewModel
import com.yusufyildiz.myapplication.ui.theme.CryptoCoinTrackerAppTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoCoinTrackerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()){ innerPadding ->
                    AdaptiveCoinListDetailPane(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
